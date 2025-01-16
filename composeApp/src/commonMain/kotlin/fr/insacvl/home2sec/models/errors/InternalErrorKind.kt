package fr.insacvl.home2sec.models.errors

enum class InternalErrorKind {
    // Device ID not found
    UNKNOWN_DEVICE,

    // SCAN failed to start
    SCAN_FAIL,

    // Device add Fail
    ADD_FAIL,

    // server fail
    SERVER_ERROR
}