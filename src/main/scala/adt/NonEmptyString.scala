package adt

opaque type NonEmptyString = String

object NonEmptyString:
    def apply(s: String): NonEmptyString = 
        if(s == null || s == "")
            throw new IllegalStateException("NonEmptyString cannot be empty or null")
        else
            s

    def safe(s: String): Either[Error.EMPTY_VALUE.type, NonEmptyString] = 
        s match
            case "" | null => Left(Error.EMPTY_VALUE)
            case somethingElse => Right(somethingElse)

extension NonEmptyStringOps on (nes: NonEmptyString):
    def equals(another: NonEmptyString): Boolean = nes == another
    def hashCode: Int = nes.hashCode
    def split(s: String): Array[NonEmptyString]
        = nes.split(s)