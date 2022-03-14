//
//class BuilderPattern (
//    val firstName : String?,
//    val lastName : String?,
//    val age : Int?
//){
//
//    data class Builder(
//        private var firstName : String? = null,
//        private var lastName : String? = null,
//        private var age : Int? = null
//    ) {
//        fun setFirstName(str : String) = apply {
//            this.firstName = str
//        }
//        fun setLastName(str : String) = apply {
//            this.lastName = str
//        }
//        fun setAge(age : Int) = apply {
//            this.age = age
//        }
//
//        fun build() =
//            BuilderPattern(firstName, lastName, age)
//    }
//}