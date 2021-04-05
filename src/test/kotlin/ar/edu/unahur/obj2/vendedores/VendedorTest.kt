package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import ar.edu.unahur.obj2.vendedores.ComercioCorresponsal as ComercioCorresponsal

class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val sanIgnacio = Ciudad(misiones)

  /* TEST VENDEDOR FIJO PUEDE TRABAJAR EN CIUDAD */
  describe("Vendedor fijo") {
    val obera = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(obera)

    describe("puedeTrabajarEn") {
      it("su ciudad de origen") {
        vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("otra ciudad") {
        vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }
  }

  /* TEST VIAJANTE PARA PUEDE TRABAJAR EN CIUDAD Y ES INFLUYENTE*/
  describe("Viajante") {
    val cordoba = Provincia(2000000)
    val villaDolores = Ciudad(cordoba)
    val viajante = Viajante(listOf(misiones))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
      it("El Viajante No es influyente en las provincias habilitadas"){
        viajante.esInfluyente().shouldBeFalse()
      }
    }
    describe("Es influyente"){
      val buenosAires = Provincia(17000000)
      val viajante1 = Viajante(listOf( buenosAires ) )
      it("El Viajante es influyente en las provincias Habilitadas"){
        viajante1.esInfluyente().shouldBeTrue()
      }
    }

  }
  /* TEST COMERCIOS CORRESPONSALES: ES INFLUYENTE */
  describe("Comercio Corresponsal"){
    val buenosAires = Provincia(17000000)
    val chivilcoy = Ciudad(buenosAires)
    val bragado = Ciudad(buenosAires)
    val lobos = Ciudad(buenosAires)
    val pergamino = Ciudad(buenosAires)
    val zarate = Ciudad(buenosAires)
    val comercioCorres = ComercioCorresponsal( listOf(chivilcoy, bragado, lobos, pergamino, zarate) )
    describe("esInfluyente") {
      it("Es influyente porque habilita en 5 ciudades") {
        comercioCorres.esInfluyente().shouldBeTrue()
      }
    }
  }
  describe("Comercio Corresponsal1"){
    val santaFe = Provincia(2700000)
    val rafaela = Ciudad(santaFe)
    val cordoba = Provincia(2300000)
    val sanFransisco = Ciudad(cordoba)
    val entreRios = Provincia(2300000)
    val diamante = Ciudad(entreRios)
    val comercioCorres1 = ComercioCorresponsal( listOf(rafaela, sanFransisco, diamante) )
    describe("esInfluyente") {
      it("Es influyente porque habilita en 3 provinciaS") {
        comercioCorres1.esInfluyente().shouldBeTrue()
      }
    }
  }
  describe("Comercio Corresponsal2"){
    val santaFe = Provincia(2700000)
    val rosario = Ciudad(santaFe)
    val rafaela = Ciudad(santaFe)
    val amstrong = Ciudad(santaFe)
    val entreRios = Provincia(2300000)
    val diamante = Ciudad(entreRios)
    val comercioCorres2 = ComercioCorresponsal( listOf(rafaela, rosario, amstrong, diamante) )
    describe("esInfluyente") {
      it("NO es influyente porque NO habilita en 3 provincias O en 5 ciudades") {
        comercioCorres2.esInfluyente().shouldBeFalse()
      }
    }
  }

  /* TEST CENTRO DISTIBUCIÓN PUEDE CUBRIR CIUDAD, ESTRELLA, GENÉRICO Y ROBUSTO */
  describe("Centro Distribución") {
    val cordoba = Provincia(2000000)
    val villaDolores = Ciudad(cordoba)
    val carlosPaz = Ciudad(cordoba)
    val viajante = Viajante(listOf(cordoba))
    val vendedorFijo = VendedorFijo(carlosPaz)
    val certificaPro = Certificacion(esDeProducto = true, puntaje = 10)
    val certificaOtra = Certificacion(esDeProducto = false, puntaje = 5)
    val certificaPro1 = Certificacion(esDeProducto = true, puntaje = 7)
    val certificaOtra1 = Certificacion(esDeProducto = false, puntaje = 13)
    val certificaPro2 = Certificacion(esDeProducto = true, puntaje = 4)
    val certificaOtra2 = Certificacion(esDeProducto = false, puntaje = 6)
    viajante.agregarCertificacion(certificaPro)
    viajante.agregarCertificacion(certificaPro1)
    viajante.agregarCertificacion(certificaOtra2)
    viajante.agregarCertificacion(certificaOtra1)
    vendedorFijo.agregarCertificacion(certificaPro2)
    vendedorFijo.agregarCertificacion(certificaOtra)
    val centroDist = CentroDistribucion(radicadoEn = villaDolores,
      vendedoresDelCentro = listOf(viajante, vendedorFijo).toMutableList())

    describe("Cubrir ciudad") {
      it("El centro de distribución puede cubrir ->CIUDAD<- ") {
        centroDist.puedeCubrirCiudad(carlosPaz).shouldBeTrue()
      }
    }
    describe("Es Robusto") {
      it("El centro de distribución No es robusto") {
        centroDist.esRobusto().shouldBeFalse()
      }
    }
    describe("Vendedor Estrella") {
      it("Viajante es estrella") {
        (centroDist.vendedorEstrella() == viajante).shouldBeTrue()
      }
    }
    describe("Vendedor Generico") {
      it("Viajante y Vendedor Fijo son Genéricos") {
        (centroDist.vendedorGenerico() == listOf(viajante, vendedorFijo)).shouldBeTrue()
      }
    }

  }
} )


