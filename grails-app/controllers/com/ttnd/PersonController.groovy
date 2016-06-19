package com.ttnd

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class PersonController {
    static responseFormats = ['json', 'xml']

    def index(String name, Integer age) {
        respond "person": new Person(name: name, age: age).save(flush: true)
    }
}
