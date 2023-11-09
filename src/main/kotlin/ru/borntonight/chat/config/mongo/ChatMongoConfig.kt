package ru.borntonight.chat.config.mongo

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(
    basePackages = ["ru.borntonight.chat.repository.chat"],
    mongoTemplateRef = "chatMongoTemplate"
)
class ChatMongoConfig