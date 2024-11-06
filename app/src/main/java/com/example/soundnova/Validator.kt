package com.example.soundnova

class Validator {
    fun checkEmail(email : String) : String {
        return if (email.isEmpty()) {
            "empty"
        } else {
            val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
            if(email.matches(emailPattern.toRegex())) "valid" else "invalid"
//        "valid"
//        "invalid"
//        "empty"
        }
    }

    fun checkPassword(password: String) : String{
        return  if (password.isEmpty()) {
            "empty"
        } else {
            val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
            if(password.matches(passwordPattern.toRegex())) "valid" else "invalid"
        }
    }
}
