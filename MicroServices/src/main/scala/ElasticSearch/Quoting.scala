package ElasticSearch

/**
  * Created by nehba_000 on 5/4/2017.
  */
sealed abstract trait Quoting extends Product with Serializable
case object QUOTE_ALL extends Quoting
case object QUOTE_MINIMAL extends Quoting
case object QUOTE_NONE extends Quoting
case object QUOTE_NONNUMERIC extends Quoting