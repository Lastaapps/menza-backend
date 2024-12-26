package cz.lastaapps.app.domain.model

data class DishDescriptor(
    val id: DishID,
    val nameCs: DishName?,
    val nameEn: DishName?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DishDescriptor) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
