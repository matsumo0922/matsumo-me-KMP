package usecase

import me.matsumo.blog.shared.entity.OgContentsEntity
import repository.OgContentsRepository

class GetOgContentsEntityUseCase(
    private val ogContentsRepository: OgContentsRepository,
) {
    suspend operator fun invoke(url: String): OgContentsEntity {
        return ogContentsRepository.getOgContents(url)
    }
}
