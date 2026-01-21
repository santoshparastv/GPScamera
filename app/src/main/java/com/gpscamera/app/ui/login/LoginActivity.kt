package com.gpscamera.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.gpscamera.app.databinding.ActivityLoginBinding
import com.gpscamera.app.data.model.User
import com.gpscamera.app.repository.FirebaseRepository
import com.gpscamera.app.ui.home.HomeActivity
import com.gpscamera.app.util.SharedPreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()
    private val firebaseRepository = FirebaseRepository()
    private lateinit var prefsHelper: SharedPreferencesHelper
    private var verificationId: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefsHelper = SharedPreferencesHelper(this)
        
        // Check if user is already logged in
        val userId = prefsHelper.getUserId()
        if (userId != null) {
            navigateToHome()
            return
        }
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Username login
        binding.btnUsernameLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            if (username.isEmpty()) {
                binding.etUsername.error = "Username is required"
                return@setOnClickListener
            }
            loginWithUsername(username)
        }
        
        // Mobile OTP login
        binding.btnSendOtp.setOnClickListener {
            val mobile = binding.etMobile.text.toString().trim()
            if (mobile.isEmpty() || mobile.length < 10) {
                binding.etMobile.error = "Valid mobile number is required"
                return@setOnClickListener
            }
            sendOtp(mobile)
        }
        
        binding.btnVerifyOtp.setOnClickListener {
            val otp = binding.etOtp.text.toString().trim()
            if (otp.isEmpty()) {
                binding.etOtp.error = "OTP is required"
                return@setOnClickListener
            }
            verifyOtp(otp)
        }
    }
    
    private fun loginWithUsername(username: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val userId = UUID.randomUUID().toString()
                val user = User(
                    userId = userId,
                    name = username,
                    role = "user"
                )
                
                val result = firebaseRepository.createUser(user)
                if (result.isSuccess) {
                    prefsHelper.saveUserId(userId)
                    prefsHelper.saveUserName(username)
                    navigateToHome()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login failed: ${result.exceptionOrNull()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@LoginActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    private fun sendOtp(mobile: String) {
        val phoneNumber = if (mobile.startsWith("+")) mobile else "+91$mobile"
        
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(phoneAuthCallbacks)
            .build()
        
        PhoneAuthProvider.verifyPhoneNumber(options)
        binding.btnSendOtp.isEnabled = false
        Toast.makeText(this, "OTP sent to $phoneNumber", Toast.LENGTH_SHORT).show()
    }
    
    private val phoneAuthCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }
        
        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@LoginActivity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
            binding.btnSendOtp.isEnabled = true
        }
        
        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            this@LoginActivity.verificationId = verificationId
            binding.btnSendOtp.isEnabled = true
            binding.llOtpSection.visibility = android.view.View.VISIBLE
        }
    }
    
    private fun verifyOtp(otp: String) {
        val credential = verificationId?.let {
            PhoneAuthProvider.getCredential(it, otp)
        } ?: run {
            Toast.makeText(this, "Verification ID not found", Toast.LENGTH_SHORT).show()
            return
        }
        signInWithPhoneAuthCredential(credential)
    }
    
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val userId = user?.uid ?: UUID.randomUUID().toString()
                    val phoneNumber = user?.phoneNumber ?: ""
                    
                    CoroutineScope(Dispatchers.Main).launch {
                        val userData = User(
                            userId = userId,
                            name = phoneNumber,
                            mobile = phoneNumber,
                            role = "user"
                        )
                        firebaseRepository.createUser(userData)
                        prefsHelper.saveUserId(userId)
                        prefsHelper.saveUserName(phoneNumber)
                        navigateToHome()
                    }
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
    
    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
