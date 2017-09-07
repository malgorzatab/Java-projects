package threads;

public class Student
{
	private double mark;
	private String firstName;
	private String lastName;
	private int age;
        
        public Student(){
            this.mark = 3.0;
            this.firstName = "Jan";
            this.lastName = "Nowak";
            this.age = 21;
        }
	
        
      
        public String toString(){
            String result = this.firstName;
            result = new StringBuilder(result).append("   "+this.lastName).toString();
            result = new StringBuilder(result).append("   "+this.mark).toString();
            result = new StringBuilder(result).append("   "+this.age).toString();
         return result;          
            
        }
        
	public double getMark()
	{
		return this.mark;
	}
	
	public void setMark( double mark )
	{
		this.mark = mark;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public void setFirstName( String firstName )
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}
	
	public int getAge()
	{
		return this.age;
	}
	
	public void setAge( int age )
	{
		this.age = age;
	}
	
	
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		
		result = prime * result + age;
		result = prime * result + ( ( firstName == null ) ? 0 : firstName.hashCode() );
		result = prime * result + ( ( lastName == null ) ? 0 : lastName.hashCode() );
		
		long temp;
		temp = Double.doubleToLongBits( mark );
		result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
		
		return result;
	}
	
	
	public boolean equals( Object obj )
	{
		if ( this == obj )
			return true;
		
		if ( obj == null )
			return false;
		
		if ( getClass() != obj.getClass() )
			return false;
		
		Student other = (Student) obj;
		
		if ( age != other.age )
			return false;
		
		if ( firstName == null )
		{
			if ( other.firstName != null )
				return false;
		}
		else 
			if ( !firstName.equals( other.firstName ) )
				return false;
		
		if ( lastName == null )
		{
			if ( other.lastName != null )
				return false;
		}
		else 
			if ( !lastName.equals( other.lastName ) )
				return false;
		
		if ( Double.doubleToLongBits( mark ) != Double.doubleToLongBits( other.mark ) )
			return false;
		
		return true;
	}
	
}
