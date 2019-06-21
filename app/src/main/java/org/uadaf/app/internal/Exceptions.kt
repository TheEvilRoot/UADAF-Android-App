package org.uadaf.app.internal

class NoConnectivityException : RuntimeException()

class UADAFServiceException(serviceName: String, serviceRequest: String) :
    RuntimeException("Service error: $serviceName: $serviceRequest")

class ITHStoryDoesNotExists(val storyID: Int) : RuntimeException("Story $storyID does not exists")