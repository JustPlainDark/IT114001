public class Loops{
	public static void main(String args[]) {
		int[] numbers = {1,2,3,4,5,6};
		int length = numbers.length;
		
		for(int i = 0; i < length; i++) {
			if (numbers[i] % 2 == 0) {
				System.out.println(numbers[i]);
			}
		}
		System.out.println("By using % or mod, I can find the remainder. If you divide any number by 2 and ");
		System.out.println("there is a remainder of 0, the number is even. So each number in the loop is tested and if it has no remainder it gets printed");
	}	
}