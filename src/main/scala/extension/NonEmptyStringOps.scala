package extension

import adt.NonEmptyString

extension NonEmptyStringOps on (nes: NonEmptyString):
    def isEqualTo(a: String): Boolean = ???