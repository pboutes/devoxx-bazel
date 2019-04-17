package fetcher


object Fetcher {

  def main(args: Array[String]): Unit = {
    println("Launching scala fetcher")
    // Useless.action()

    WebServer.startServer("0.0.0.0", 3000)

  }

}