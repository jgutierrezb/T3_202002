package model.data_structures;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.io.*;
import java.util.*;
import java.lang.*;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.util.OpencsvUtils;
import com.opencsv.exceptions.CsvException;

/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 * @author Fernando De la Rosa
 *
 */
public class ArregloDinamico<T extends Comparable<T>> implements ILista {
		/**
		 * Capacidad maxima del arreglo
		 */
        private int tamanoMax;
		/**
		 * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
		 */
        private int tamanoAct;
        /**
         * Arreglo de elementos de tamaNo maximo
         */
        private String elementos[];
        
        private List<String[]> nuevosDatosActores;
        
        private List<String[]> nuevosDatosPeliculas;

        //TODO EN ARREGLO DINAMICO VA LAS LINEAS DE TEXTO CON ELEMENTOS INDIVIDUALES Y EN MODELO VA EN SI EL "CATALOGO"
        
        private static final String CSV_2 = "./data/SmallMoviesDetailsCleaned.csv";
        private static final String CSV_1 = "./data/MoviesCastingRaw-small.csv";
		private String[] listaFinals;
		private String[] listas;
        /**
         * Construir un arreglo con la capacidad maxima inicial.
         * @param max Capacidad maxima inicial
         */
		public ArregloDinamico( int max )
        {
               elementos = new String[max];
               tamanoMax = max;
               tamanoAct = 0;
               
        }
        
		public void agregar( String dato )
        {
               {  // caso de arreglo lleno (aumentar tamaNo)
                    tamanoMax = 2 * tamanoMax;
                    String [ ] copia = elementos;
                    elementos = new String[tamanoMax];
                    for ( int i = 0; i < tamanoAct; i++)
                    {
                     	 elementos[i] = copia[i];
                    } 
            	    System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
               }	
               elementos[tamanoAct] = dato;
               tamanoAct++;
       }

		public int darCapacidad() {
			return tamanoMax;
		}

		@Override
		public void addFirst(Comparable element) {
			insertElement( element, 0);
		}

		@Override
		public void addLast(Comparable element) {
			insertElement(element, tamanoAct);
		}

		@Override
		public void insertElement(Comparable element, int pos) {
			
			int posicionActual = tamanoAct - 1;
			
			elementos[pos] = (String) element;
			if (tamanoAct >= tamanoMax){
				tamanoMax++;
				
				for (int i = posicionActual; i > 0 && i < pos; i--) {
					
					elementos [posicionActual] = elementos [posicionActual+1];
					posicionActual--;
					
				}
				
				elementos[pos] = (String) element;
			}
			
		}

		@Override
		public Comparable removeFirst() {
			
			Comparable elementoSacado = elementos[0];
			
			for (int i = 0; i < elementos.length; i++) {
				elementos[i] = elementos[i+1];
			}
			
			tamanoAct--;
			
			
			return elementoSacado;
		}

		@Override
		public Comparable removeLast() {
			
			elementos [tamanoAct] = null;
			
			return elementos[tamanoAct];
		}

		@Override
		public Comparable deleteElement(int pos) {
			elementos[pos] = null;
			return elementos[pos];
		}

		@Override
		public Comparable firstElement() {
			return elementos[0];
		}

		@Override
		public Comparable lastElement() {
			return elementos[tamanoAct];
		}

		@Override
		public Comparable getElement(int pos) {
			return elementos[pos];
		}

		@Override
		public int size() {
			return tamanoAct;
		}

		@Override
		public boolean isEmpty() {
			boolean retorno = false;
			
			if (tamanoAct != 0){
				retorno = !retorno;
			}
			
			return retorno;
		}

		public int buscar(Comparable element) {
			
			int posicionARetornar = 0;
			
			for (int i = 0; i < elementos.length; i++) {
				if (elementos[i].compareTo((String) element) == 1){
					
					posicionARetornar = i;
				}
			}	
			
			return posicionARetornar;
		}

		@Override
		public void exchange(int pos1, int pos2) {
			String copiaElemento1 = null;
			if (pos1 != pos2 && pos1 >= 0 && pos1 < tamanoAct && pos2 >= 0 && pos2 < tamanoAct){
				copiaElemento1 = elementos[pos1];
				elementos[pos1] = elementos[pos2];
				elementos[pos2] = copiaElemento1;
			}
			
		}

		@Override
		public void changeInfo(int pos, Comparable elem) {
			if (pos >= 0 && pos < tamanoAct){
				elementos[pos] = (String) elem;
			}
			
		}

		@Override
		public String buscarS(String dato) {
			return elementos[buscar(dato)];
		}

		@Override
		public String eliminar(String dato) {
			
			deleteElement(buscar(dato));
			
			return elementos[buscar(dato)];

		}
		
		public void crearListaDeElementosActores(){
			
			BufferedReader br = null;
			try {
				
				br = new BufferedReader(new FileReader (CSV_1));
				CSVReader reader = new CSVReader(new FileReader(CSV_1));
				nuevosDatosActores = reader.readAll();
				
				System.out.println(nuevosDatosActores.size());
			} catch (FileNotFoundException e){
				System.out.println("No se encontro el archivo indicado");
				e.printStackTrace();
			} catch (IOException e){
				System.out.println("IOException");
				e.printStackTrace();
			} catch (CsvException e) {
				System.out.println("Se produjo una excepcion de tipo CSV");
				e.printStackTrace();
			} finally {
				if ( br != null){
					
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}
			
		}
		
		public void crearListaDeElementosPeliculas(){
					
					try {
						
						CSVReader reader = new CSVReader(new FileReader(CSV_2));
						nuevosDatosPeliculas = reader.readAll();
						System.out.println(nuevosDatosPeliculas.size());
						
					} catch (FileNotFoundException e){
						System.out.println("No se encontro el archivo indicado");
						e.printStackTrace();
					} catch (IOException e){
						System.out.println("IOException");
						e.printStackTrace();
					} catch (CsvException e) {
						System.out.println("Se produjo una excepcion de tipo CSV");
						e.printStackTrace();
					}
				}
		
		
		public int[] encontrarPeliculasPorDirector(String pNombreDirector){
			
			int[] listaFinal = null;
			String[] datos = null;
			int j = 0;
			
			for (int i = 1; i < nuevosDatosActores.size(); i++){
				
				datos = nuevosDatosActores.get(i).toString().split(",");
				
				if (datos[12].equalsIgnoreCase(pNombreDirector)){
					
					listaFinal[j] = i;
					j++;
				}
			}
			
			return listaFinal;
		}
		
		public int encontrarBuenasPeliculas(String pNombreDirector){
			int[] listaAUsar = null;
			String[] datos = null;
			int totalPeliculasBuenas = 0;
			int contador = 0;
			
			String nombreDirector = pNombreDirector;
			

			for(int i = 0; i < listaAUsar.length; i++){
				
				datos = nuevosDatosPeliculas.get(listaAUsar[i]).toString().split(";");
				
				if (Integer.parseInt(datos[17]) <= 6){
					contador++;
				}
			}
			return contador;
		}
		
		public String crearRankingVotos(){
			String[] datos = null;
			
			for (int i = 0; i < nuevosDatosPeliculas.size(); i++) {
				
				datos = nuevosDatosPeliculas.get(i).toString().split(";");
				
				int promedioVotos = Integer.parseInt(datos[17]);
				int cuentaVotos = Integer.parseInt(datos[18]);
				
				
				
			}
			quickSortRankingVoteCount(datos, 0, datos.length);
			
			return datos[0] + datos[1]
							+ datos[2]
							+ datos [3]
							+ datos[4]	;
		}
		
		
		
		

		
		
		
		public void shellShortWorstMovies(String[] pArray) {
			int n = pArray.length;
			
			int i= 0;
			
			int j=0;
			int actual =0;
		String[]	valoresVotos;
		List<Integer> listaAUsar;
		
			for(int l = 0; l < pArray.length; l++) {
				valoresVotos = nuevosDatosPeliculas.get(l).toString().split(",");
				int  v = Integer.parseInt(valoresVotos[18]);
				listaAUsar.add(0, v);
				listaAUsar.toArray();
				
				v =0;
				
		
		

			
			int valorI = Integer.parseInt(pArray[18]);
		
			
			for(int gap = n/2 ; gap > 0; gap /= 2 )
			{
				for( i= gap ; i<n ; i+= 1)
				{
					int[] temp =  listaAUsar[i];
				
					for(j = i; j>= gap && temp.compareTo(listaAUsar[j - gap])  < 0 ; j -= gap) {
						pArray[j]= pArray[j - gap];
					
						
					}
					pArray[j] = temp;
				
				}
			}
			
		}
		
		public void quickSortRankingVoteCount( String[] pArray, int pLow, int pHigh ){
			
			int low = pLow;
			int high = pHigh;
			
			
			
			if (pArray == null || pArray.length == 0)
				return;
	 
			if (low >= high)
				return;
			
			int mitad = low + (high - low) / 2;
			
			String[] linea = pArray[mitad].toString().split(";");
			
			int pivot =  Integer.parseInt(linea[18]);
			
			int i = low;
			int j = high;
			
			while (i <= j ){
				String[] lineaActualI = pArray[i].toString().split(";");
				String[] lineaActualJ = pArray[j].toString().split(";");
				
				int valorI = Integer.parseInt(lineaActualI[18]);
				int valorJ = Integer.parseInt(lineaActualJ[18]);
				
				while (valorI < pivot){
					i++;
				}
				
				while (valorJ > pivot){
					j--;
				}
				
				if (i <= j){
					String temp1 = pArray[i];
					int temp = valorI;
					pArray[i] = pArray[j];
					pArray[j] = temp1;
					i++;
					j--;
					
				}
			}
			
			if (low < j){
				quickSortRankingVoteCount(pArray, low, j);
			}
			if (high < j){
				quickSortRankingVoteCount(pArray, i, high);
			}
		}
		
		public String[] conocerAUnDirector(String pNombreDirector){
			
			String coma = ", ";
			int j = 0;
			double sumatoria = 0;
			double promedio = 0;
			String[] listaFinal = null;
	
			for (int i = 1; i < elementos.length; i++) {
				String[] datosActores = nuevosDatosActores.get(i).toString().split(",");
				String[] datosPeliculas = nuevosDatosPeliculas.get(i).toString().split(";");
				
				
				
				String nombrePelicula = datosPeliculas[5];
				String idPelicula = datosPeliculas[0];
				
				int valor = Integer.parseInt(datosPeliculas[17]);
	
				if (datosActores[12].equalsIgnoreCase(pNombreDirector)){
					
					listaFinal[j] = nombrePelicula + coma + idPelicula; 
					
					j++;
					
					sumatoria += valor;
				}
				promedio = sumatoria/listaFinal.length;
			}
			
			System.out.println("El promedio de calificacion de sus peliculas fue de: " + promedio);
			return listaFinal;
			
		}
		
		public String[] conocerAUnActor(String pNombreActor)
		{
			
			String coma = ", ";
			int j = 0;
			double sumatoria = 0;
			double promedio = 0;
			String[] listaFinal = null;
			
			for ( int i = 1; i < elementos.length ; i++ ) 
			{
				String[] datosActores = nuevosDatosActores.get( i ).toString().split( "," );
				String[] datosPeliculas = nuevosDatosPeliculas.get( i ).toString().split( ";" );
				
				
				
				String nombrePelicula = datosPeliculas[ 5 ];
				String idPelicula = datosPeliculas[0];
				
				int valor = Integer.parseInt( datosPeliculas[ 17 ] );
				
				if ( datosActores[ 1 ].equalsIgnoreCase( pNombreActor ) || datosActores[ 3 ].equalsIgnoreCase( pNombreActor ) || datosActores[ 5 ].equalsIgnoreCase( pNombreActor ) || datosActores[ 7 ].equalsIgnoreCase( pNombreActor ) || datosActores[ 9 ].equalsIgnoreCase( pNombreActor ))
				{
					
					listaFinal[ j ] = nombrePelicula + coma + idPelicula; 
					
					j++;
					
					sumatoria += valor;
				}
				promedio = sumatoria/listaFinal.length;
			}
			
			System.out.println("La cantidad de peliculas es de: " + listaFinal.length + "." + "El promedio de calificacion de las peliculas en las que actuó fue de: " + promedio + "." );
			return listaFinal;
		}
		
		public String[] directorConMasColaboraciones( String pNombreActor )
		{
			String coma = ", ";
			int j = 0;
			double sumatoria = 0;
			String[] listaFinal = null;
			
			for ( int i = 1; i < elementos.length ; i++ ) 
			{
				String[] datosActores = nuevosDatosActores.get( i ).toString().split( "," );
				String[] datosPeliculas = nuevosDatosPeliculas.get( i ).toString().split( ";" );
				
				
				
				String nombrePelicula = datosPeliculas[5];
				String idPelicula = datosPeliculas[0];
				
				int valor = Integer.parseInt( datosPeliculas[17] );
				
				if ( datosActores[ 1 ].equalsIgnoreCase( pNombreActor ) || datosActores[ 3 ].equalsIgnoreCase( pNombreActor ) || datosActores[ 5 ].equalsIgnoreCase( pNombreActor ) || datosActores[ 7 ].equalsIgnoreCase( pNombreActor ) || datosActores[ 9 ].equalsIgnoreCase( pNombreActor ))
				{
					
					listaFinal[ j ] = nombrePelicula + coma + idPelicula; 
					
					j++;
					
					sumatoria += valor;
				}
			}
			System.out.println("El director con quien mas colaboro el actor es: "  );
			return listaFinal;
		}
		
		public String[] entenderGenero( String pGenero )
		{
			String coma = ", ";
			int j = 0;
			String[] lista = null;
			int cantidadPorGenero = 0;
			
			for( int i = 1 ; i < elementos.length ; i++ )
			{
				String[] datosActores = nuevosDatosActores.get( i ).toString().split( "," );
				String[] datosPeliculas = nuevosDatosPeliculas.get( i ).toString().split( ";" );
				
				String nombrePelicula = datosPeliculas[5];
				String idPelicula = datosPeliculas[0];
				String generoPelicula = datosPeliculas[ 3 ];
				
				if( generoPelicula.equalsIgnoreCase( pGenero ) )
				{
					lista[ j ] = nombrePelicula + coma + idPelicula;
					
					j++;
					
					cantidadPorGenero++;
				}
			}
			System.out.println( "El numero de peliculas de este genero es: " + cantidadPorGenero + " . ");
			return lista;
		}
		//Proyecto 2 
		public  String[] descubrirProductorasDeCine(String pNombre) {
			String coma = ", ";
			int j = 0;
			int sumatoria = 0;
			listaFinals = null;
			
			for ( int i = 1; i < elementos.length ; i++ ) 
			{
				
				String[] datosPeliculas = nuevosDatosPeliculas.get( i ).toString().split( ";" );
				
				
				
				String productora = datosPeliculas[8];
				String promedioVotos = datosPeliculas[17];
				String nombrePelicula = datosPeliculas[5];
				
				
				
				if ( productora.equals(pNombre)){
					
					listaFinals[ j ] = nombrePelicula + coma + sumatoria + coma + promedioVotos ; 
					
			sumatoria++;
					
				}
				if(i == elementos.length) {
					for(int k =0;k < listaFinals[j].length(); k++) {
					
						int valorVoto= 0;
						listaFinals[j].split(coma);
					int	promedioVotoIndividual = Integer.parseInt(listaFinals[3]);
						valorVoto = valorVoto + promedioVotoIndividual;
						valorVoto = valorVoto/sumatoria;
						 
						listaFinals[ j ] = nombrePelicula+ coma + sumatoria + coma + promedioVotos + valorVoto;
					}
				}
				
			}
			
			return listaFinals;
		
}
		//El conocer a un director del reto 1 es mas rapido por la cantidad de memoria que se utiliza en la parte del promedio de esta.
	public String[] conocerAUnDirectorReto2(String pNombreDirector){
			
			String coma = ", ";
			int j = 0;
			int sum =0;
			
			
			listas = null;
	
			for (int i = 1; i < elementos.length; i++) {
				String[] datosActores = nuevosDatosActores.get(i).toString().split(",");
				String[] datosPeliculas = nuevosDatosPeliculas.get(i).toString().split(";");
				
				
				
				String nombrePelicula = datosPeliculas[5];
				String idPelicula = datosPeliculas[0];
				
				int valor = Integer.parseInt(datosPeliculas[17]);
	
				if (datosActores[12].equalsIgnoreCase(pNombreDirector)){
					
					listas[j] = nombrePelicula + coma + idPelicula + valor; 
					
					j++;
				sum++;
					
				}
				int vV=0;
				if(elementos.length == i) {
					for(int y=0; y < listas.length; y++) {
					
						listas[j].split(",");
						int valorIndividual = Integer.parseInt(listas[3]);
						vV = (vV + valorIndividual)/sum;
						
						
					}
				}
				listas[j] = nombrePelicula + coma + idPelicula + vV;
			}
			
			
			return listas;
			
		}
	
	
}
		
