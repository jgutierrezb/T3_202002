package model.data_structures;



	public class TablaHashSeparateChaining <K extends Comparable<K>,V extends Comparable<V>> implements TablaSimbolos<K, V> {

	
		
		class Nodo <K extends Comparable<K>,V extends Comparable<V>>
		{
			
			private K key;
			
			private V value;
			
			private Nodo <K,V>siguiente;
			
			public Nodo(K pKey, V pValue){
			
				this.key=pKey;
				
				this.value=pValue;
				
			}	
			public K getLlave() {
				return key;
			}

			public void setLlave(Comparable llave) {
				this.key = (K) key;
			}

		

			@SuppressWarnings("unchecked")
			public void setValor(Comparable value) {
				this.value = (V) value;
			}

			public Nodo<K, V> getNodoSiguiente() {
				return siguiente;
			}

			public void setNodoSiguiente(Nodo<K, V> nodoSiguiente) {
				this.siguiente = nodoSiguiente;
			}
			public V getValor() {
				// TODO Auto-generated method stub
				return null;
			}

		
			
		}

		
		private Nodo <K,V>[] elements;
		
		
		
		private int tamanototal=2466;
		private int tamanoparejas;
		
		@SuppressWarnings("unchecked")
		public TablaHashSeparateChaining() {
	
		
			elements= new Nodo[tamanototal] ;
			tamanoparejas=0;
			
		}
		public int hash(@SuppressWarnings("rawtypes") Comparable key)
		{
			return (key.hashCode() & 0x7fffffff)%tamanototal;
		}
		
		public void manejararreglo()
		{
			if(tamanoparejas/tamanototal>=5.0)
			{
				Nodo<K, V>[] copia= elements;
				int i=0;
				tamanototal=tamanototal*2;
				elements=  new Nodo[tamanototal];
				for(Nodo<K, V> elements1: copia)
				{
					elements[i]=elements1;
					i++;
				}
				 
			}
		}
		public void put(K key, V value) {
			
			manejararreglo();
			
			int i= hash(key);
			 Nodo <K,V> actual = elements[i];
			tamanoparejas++;
			Nodo <K,V> agregar= new Nodo(key,value);
			agregar.setNodoSiguiente(actual);
			actual=elements[i];
			elements[i]=agregar;
		}

		public V get(K key) {
		
			manejararreglo();
			int i= hash(key);
			 Nodo <K,V> actual = elements[i];
				 while(actual!=null)
				 	{
					 if(key.equals(actual.getLlave()))
						{
						 return actual.getValor();
						 
						}
					 actual=actual.getNodoSiguiente();
				 	}
			 
			 return null;
		}


}
