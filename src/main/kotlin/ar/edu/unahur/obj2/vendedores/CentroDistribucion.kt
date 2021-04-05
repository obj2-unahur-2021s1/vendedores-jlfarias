package ar.edu.unahur.obj2.vendedores

// de cada uno se sabe en qué ciudad está, y con qué vendedores trabaja
class CentroDistribucion(radicadoEn:Ciudad , val vendedoresDelCentro: MutableList<Vendedor>){

    // AGREGA 1 VENDEDOR A LA LISTA
    fun agregarVendedor(vendedor:Vendedor) = vendedoresDelCentro.add(vendedor)

    // RETORNA 1Vendedor
    // El que tiene  mayor puntaje total por certificaciones.
    fun vendedorEstrella()  = vendedoresDelCentro.maxBy { v -> v.puntajeCertificaciones() }

    // RETORNA BOOLEANO
    // Al menos uno de los vendedores registrados pueda trabajar en esa ciudad.
    fun puedeCubrirCiudad(ciudad:Ciudad) = vendedoresDelCentro.any { v -> v.puedeTrabajarEn(ciudad) }

    // RETORNA List<vendedores>
    //si tiene al menos una certificación que no es de productos
    fun vendedorGenerico() = vendedoresDelCentro.filter { v -> v.otrasCertificaciones() >= 1 }

    // RETORNA BOOLEANO
    //al menos 3 de sus vendedores registrados son firmes
    fun esRobusto() = vendedoresDelCentro.count { v -> v.esFirme() } > 3
}