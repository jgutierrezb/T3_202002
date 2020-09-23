package model.data_structures;

public class TablaHashLinearProbing <K extends Comparable<K>,V extends Comparable<V>> implements TablaSimbolos<K, V> {

	private int tamanoPares;
	private int numeroArreglo=2465;
	
	private K [] keys;
	private V [] values;
	
	
	public  TablaHashLinearProbing () {
	
		tamanoPares=0;
		keys= (K[]) new Comparable[numeroArreglo];
		values= (V[]) new Comparable[numeroArreglo];
		
	}
	public void manejarArreglo()
	{
		if(tamanoPares/numeroArreglo>=0.75)
		{
			K [] copy;
			int i=0;
			copy=keys;
			numeroArreglo=numeroArreglo*2;
			keys=(K[]) new Comparable[numeroArreglo];
			for(K elements: copy)
			{
				keys[i]=elements;
				i++;
			}
			V [] copyValue;
			int j=0;
			copyValue=values;
			values= (V[]) new Comparable[numeroArreglo];
			for(V elements: copyValue)
			{
				values[j]=elements;
				j++;
			}
		}
	}
	
	public int hash(Comparable key)
	
	{
		return (key.hashCode() & 0x7fffffff)%numeroArreglo;
	}
	public V get(K key) {
		
		manejarArreglo();
		int i= hash(key);
		while(keys[i]!=null)
		{
			if(keys[i].equals(key))
			{
				return values[i];
			}
			i=(i+1)%numeroArreglo;
		}
		
		return null;
	}


	public void put(K key, V value) {
		
		manejarArreglo();
		int i= hash(key);
		ArregloDinamico<V> valoresRepetidos;
		int contador=1;
		if(keys[i]==null)
		{
			keys[i]= key;
			values[i]= value;
			tamanoPares++;	
		}
		else
		{
			while(keys[i]!=null)
			{
				i=(i+1)%numeroArreglo;
			}
			keys[i]= key;
			values[i]= value;
			tamanoPares++;
			
		}
		
	}	
	public ArregloDinamico<K> keySet() {
	
		ArregloDinamico<K> n= new ArregloDinamico<K>(2000);
		for(int i=0; i<keys.length;i++)
		{
			n.insertElement(keys[i], i+1);
		}
		return n;
	}

	public ArregloDinamico<V> valueSet() {
		ArregloDinamico<V> n= new ArregloDinamico<V>(2001);
		int contador=1;
		for(int i=0; i<values.length;i++)
		{
			if(values[i]!=null)
			{
				n.insertElement(values[i], contador);
				contador++;
			}
		}
		return n;
	}

}

