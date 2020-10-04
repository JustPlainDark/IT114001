public class Sum {
  
	public static int sum(int num) {
		int sum = 0;
		for (int i = num; i > 0; i--) {
			sum = sum + i;
		}
		return sum;
	}
	
	public static int recursion(int num) {
		if (num > 0) {
			return num + sum(num - 1);
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(sum(10));
		System.out.println(recursion(10));
	}
}