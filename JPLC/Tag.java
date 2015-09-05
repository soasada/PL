public class Tag{

		public static final int MAQ = 1;
		public static final int MEQ = 2;
		public static final int EQ = 3;
		public static final int MEOQ = 4;
		public static final int MAOQ = 5;
		public static final int NEQ = 6;

		private String v;
		private String f;

		public Tag(String v, String f){
				this.v = v;
				this.f = f;
		}

		public String getV(){
				return this.v;
		}

		public String getF(){
				return this.f;
		}

		public void setV(String v){
				this.v = v;
		}

		public void setF(String f){
				this.f = f;
		}
}
