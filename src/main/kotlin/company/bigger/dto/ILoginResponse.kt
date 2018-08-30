package company.bigger.dto

interface ILoginResponse {
    val logged: Boolean
    val token: String?
}