### 객체지향 프로그램

- 객체 : 동일한 성질의 데이터와 메소드를 한곳에 모아두고 필요한 곳에서 언제든지 이용할 수 있게  만들어 놓은 덩어리



### 메소드

```java
// public은 어디서든 사용 가능하다는 접근 제한자
// private은 동일한 클래스에서만 사용 가능하다는 접근 제한자
public int /* 반환타입, 반환값이 없으면 void */ sum(int i, int j /* 파라미터 (지역변수) */) {
    int r = 0;
    for (int h = i;h <= j; h++) {
        r = r + h
    }
    return r;
}
```



### ex

```java
public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		
		GuGuDan guGuDan = new GuGuDan();
    	// 호출부
		guGuDan.gugudan(input);
		
	}
	
	// 실행 메소드
	public void gugudan(int i) {
		// TODO Auto-generated method stub
		for(int j=1; j<10; j++){
			System.out.println(i + " * " + j + " = " + (i * j));
		}
		
	}
```

