package ar.edu.unahur.obj2.vendedores

class Certificacion(val esDeProducto: Boolean, val puntaje: Int)

abstract class Vendedor {
  // Acá es obligatorio poner el tipo de la lista, porque como está vacía no lo puede inferir.
  // Además, a una MutableList se le pueden agregar elementos
  val certificaciones = mutableListOf<Certificacion>()

  // Definimos el método abstracto.
  // Como no vamos a implementarlo acá, es necesario explicitar qué devuelve.
  abstract fun puedeTrabajarEn(ciudad: Ciudad): Boolean

  // RETORNA BOOLEANO
  // En las funciones declaradas con = no es necesario explicitar el tipo
  fun esVersatil() =
    certificaciones.size >= 3
      && this.certificacionesDeProducto() >= 1
      && this.otrasCertificaciones() >= 1

  // Si el tipo no está declarado y la función no devuelve nada, se asume Unit (es decir, vacío)
  fun agregarCertificacion(certificacion: Certificacion) {
    certificaciones.add(certificacion)
  }
  // RETORNA BOOLEANO
  fun esFirme() = this.puntajeCertificaciones() >= 30

  // RETORNA UN ENTERO
  fun certificacionesDeProducto() = certificaciones.count { it.esDeProducto }

  // RETORNA UN ENTERO
  fun otrasCertificaciones() = certificaciones.count { !it.esDeProducto }

  // RETORNA UN ENTERO
  fun puntajeCertificaciones() = certificaciones.sumBy { c -> c.puntaje }

  abstract fun esInfluyente(): Boolean

}

// En los parámetros, es obligatorio poner el tipo. Inherits es :
class VendedorFijo(val ciudadOrigen: Ciudad) : Vendedor() {

  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudad == ciudadOrigen
  }

  override fun esInfluyente(): Boolean {
    return false
  }
}

// A este tipo de List no se le pueden agregar elementos una vez definida
class Viajante(val provinciasHabilitadas: List<Provincia>) : Vendedor() {

  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return provinciasHabilitadas.contains(ciudad.provincia)
  }

  /* Viajante poblacion(suma de las Prov) > 10000000.*/
  override fun esInfluyente(): Boolean{
  val cantidadPoblacion: Int = provinciasHabilitadas.sumBy { p -> p.poblacion }
  return cantidadPoblacion > 10000000
  }
}

class ComercioCorresponsal(val ciudades: List<Ciudad>) : Vendedor() {

  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudades.contains(ciudad)
  }

  //C.Corresponsal 5 ciudades o 3 provincias
  /**/
  override fun esInfluyente(): Boolean {
  val cantidadCiudades: Int  =  ciudades.size
  val cantidadProvincias: Int = ciudades.map { c -> c.provincia }.toSet().size // a conjunto
  return cantidadCiudades >= 5 || cantidadProvincias >= 3
  }
}

