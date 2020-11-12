package excel

class Data (val id: String = "",
            val name: String = "",
            val address: String = "",
            val age: Int = 0) {
    override fun toString(): String {
        return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", age=" + age + "]"
    }
}