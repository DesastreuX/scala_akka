trait RepositoryComponent {
    implicit val mongo = new OrderMongoRepository
}
