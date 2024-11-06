package com.example.soundnova

class Validator {
    fun checkEmail(email : String) : String {
//        "valid"
//        "invalid"
//        "empty"
        if (email.isNotEmpty()) {
            if (isValidEmail(email)) {
                return "valid";
            }
            return "invalid";
        }
        return "empty"
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return Regex(emailRegex).matches(email)
    }


    fun checkPassword(password: String) : String{
        if (password.isNotEmpty()) {
            if (isValidPassword(password)) {
                return "valid"
            }
            return "invalid"
        }
        return "empty"
    }

    fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
        return Regex(passwordRegex).matches(password)
    }

}

fun main() {
    val email : String = "user@gmail.com"
    val password: String = "Cong23032004*"
    val validator = Validator()

    println(validator.isValidEmail(email))
    println(validator.isValidPassword(password))
}

