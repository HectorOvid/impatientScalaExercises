// import java.awt.datatransfer._ and make an object of type SystemFlavorMap with the call
// val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
import java.awt.datatransfer._

val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
val images = flavors.getNativesForFlavor(DataFlavor.imageFlavor)
println(images)

import scala.collection.JavaConverters._

images.asScala.toBuffer
