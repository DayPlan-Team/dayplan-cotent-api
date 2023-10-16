package com.content.api.publics

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/reviewgroups")
class ReviewWriterController {

    @PostMapping("/{reviewGroup}/writer")
    fun writerReview() {
        TODO("")
    }

}