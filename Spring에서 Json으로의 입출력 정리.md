## Spring에서 Json으로의 입출력 정리

```
1. Java 객체에 저장된 값을 Json 형태로 변환하여 Request Body에 실어서 보내야 한다(이때 String 형 변수가 아닌 다른 형(ex : int 형)을 String 형태(쌍따옴표로 감싼 형태 : "123")로 보내야 한다.

2. Java 객체에 저장된 값을 Json 형태로 변환하여 보낼때 선별적으로 보낼수 있어야 한다. (무슨뜻이냐면 Json으로 보낼때 모든 필드를 다 사용하는것이 아니라 특정 필드는 제외해서 사용할 수 있어야...)

3.  Request의 Body로 온 Json 내용을 Java 객체로 매핑해서 이용한다
```



일상적으로 프로젝트를 진행하면서 JSON 문자열을 만드는 방법은 여러가지 방법이 있다.

JSON 문법을 아는 사람이라면 아예 String 변수에 JSON 문법 형태가 적용된 문자열을 만들어서 넣는 아주 로우레벨 스타일도 있을수 있고..

나같이 문법을 잘 몰라서 라이브러리 도움을 받아야 하는 사람이라면 JSON 문자열로 표현할 내용을 담은 VO에 JSON 관련 어노테이션들을 붙여서 이를 통해 JSON 문자열을 만드는 방법도 있을 것이다..

일단 나는 후자의 방법을 선택했다..



일단 다음의 예시 VO를 보자..

다음의 VO는 내가 JSON으로 보내고자 하는 내용을 담은 객체에 JSON 처리 관련 어노테이션이 붙은 것이다..

JSON 관련 어노테이션을 빼고 본다면 우리가 Spring에서 다루는 일반적인 VO와 차이가 없다..



```java
@JsonAutoDetect(fieldVisibility=Visibility.NONE,
                getterVisibility = Visibility.NONE,
                setterVisibility = Visibility.NONE)
@JsonPropertyOrder({"unit_id", "unit_code", "lvl", "name",
                    "regDate", "orders", "imgPath", "status"})
public class UnitVO extends CommonVO implements Cloneable {
 
     @JsonProperty("unitID") @JsonSerialize(using = ToStringSerializer.class)
     int unit_id;                  
     @JsonProperty("unitCode")
     String unit_code;             
     @JsonProperty("lvl") @JsonSerialize(using = ToStringSerializer.class)
     int lvl;                       
     @JsonProperty("name")
     String name;                  
     @JsonProperty("regDate")
     String reg_date;             
     @JsonProperty("orders") @JsonSerialize(using = ToStringSerializer.class)
     int orders;                       
     @JsonProperty("imgPath")
     String img_path;             
     @JsonProperty("status") @JsonSerialize(using = ToStringSerializer.class)
     int status;                       
     
     int apistatus;                  
     
     String upper_unit_code;        
     int subcnt;                       
     String statusdesc;             
     String loginid;                  
      
     /* getter와 setter는 생략 */
}
```



-  **@JsonPropertyOrder**는 Json 문자열로 표현할때 표현되는 멤버필드의 순서를 정하는 것이다. 

  위의 예제대로 한다면 Json 문자열로 표현할때 unit_id, unit_code, lvl ... 순으로 Json 문자열을 만들것이다.

  이때 주의할 점은 **@JsonPropertyOrder에 넣어야 할 값들은 @JsonProperty 어노테이션으로 설정되는 Json 필드명이 아니라... 클래스의 필드명으로 주어야 한다**는 것이다.

  

-  **@JsonProperty** 어노테이션이 가장 핵심적인 역할을 한다. 이 어노테이션이 붙은 멤버필드, getter, setter에만 JSON 관련 작업을 하게 되는 것이다. 즉 JSON 문자열로 출력하거나 또는 JSON 문자열에서 읽어들이는 것이다.

   예를 들어 **@JsonProperty("unitID")** 라고 멤버필드에 붙이면 unitID란 Key로 해당 필드의 값을 JSON으로 출력하거나 또는 JSON 문자열에서 unitID란 Key에 해당되는 값을 읽어 멤버 필드에 넣게 된다. 멤버 필드에서 설정하게 되면 JSON 문자열로 읽고 쓰는 작업 모두를 할수 있으며 getter 함수에 붙인다면 JSON 문자열로 출력하는 작업만 가능하며, setter 함수에 붙인다면 JSON 문자열에서 읽어들이는 작업만 가능하게 된다.  

   지금의 예에서는 **@JsonProperty("unitID")**라고 설정해서 필드 변수명과 Json Key 이름을 달리 주었으나 이 Key를 생략하면 필드 변수명 동일하게 Json Key 이름을 사용하게 된다.

  

-  **@JsonSerializer**는 Json 문자열로 출력할때 특별한 작업을 할 상황이 있을 경우**org.codehaus.jackson.map.JsonSerializer** 클래스를 상속받아 관련 기능을 구현하면 된다. 자바 객체에서 JSON 문자열로 만드는 것을 **Serialize**, JSON 문자열에서 자바 객체로 만드는 것을 **Deserialize**라고 한다. 

   그래서 **org.codehaus.jackson.map.JsonDeserializer** 라는 클래스도 존재한다.(물론 기능은 JsonSerializer 클래스가 하는 것과는 정반대의 역할일것이다)

   암튼 정리하자면 Json 문자열로 출력할때 특별한 작업을 할 상황이 있다면 JsonSerialize 클래스를 상속받은 클래스를 만들어서 관련 기능을 구현한뒤 이를 **@JsonSerializer** 어노테이션의 using 속성에 관련 클래스 이름을  명시해주면 된다. 

   이걸 쓴 이유는 위에서 얘기했을때 String 변수가 아닌 자료형일 경우 강제로 String 형으로 만들어서 JSON 문자열에서 표현한다고 했었다. 그 기능을 하기 위해 JsonSerializer 클래스를 상속받은 별도 클래스를 만든 것이다. 다음의 코드를 보면 좀더 이해가 될 것이다.

  

```java
import java.io.IOException;
 
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
 
public class ToStringSerializer extends JsonSerializer<Object> {
 
    @Override
    public void serialize(Object value, JsonGenerator jgen,
         SerializerProvider provider) throws IOException,
         JsonProcessingException {
             jgen.writeObject(value.toString());
      }
}
```



-  value 란 변수에는 우리가 VO의 필드멤버에서 읽은 값이 들어오게 된다. 이를 toString 함수를 이용해서 String 문자열로 변환된 값을 읽은후 writeObject를 이용해서 JSON 문자열로로 출력하게 된다. 

   그러면 int 형이래도 (ex : 3333) String 형으로 (ex : "3333") 출력시켜주는 것이다. 이렇게 JSON으로 변환한 문자열을 HttpUrlConnection 클래스를 이용해 Body에 실어보내면 그걸 받는 Spring 쪽에서는 어떻게 할 것인가????...  의외로 간단하다.

   다음의 코드는 Spring의 Controller에서 JSON 문자열을 받아서 이를 객체에 넣는  함수이다.



```java
  @RequestMapping(value="/group/{groupId}", method=RequestMethod.POST)
  public ResultVO myUnit(
      @PathVariable("groupId")
      String unitCode,
     @RequestBody UnitVO objUnit,
      HttpServletRequest request
      ) throws Exception {
   ....
}
```



-  위의 코드를 보면 **@RequestBody** 란 어노테이션이 있다. 이 어노테이션은 Http Request Body에 있는 내용을 **@RequestBody** 어노테이션 다음에 명시하는 클래스에 매핑해주게 된다. 만약 **@RequestBody** String strBody 요래버리면 Request Body에 있는 문자열을 그대로 String에 담아주게 된다. 그러면 String이 아닌 UnitVO와 같은 사용자 클래스라면? 그렇다면 그 클래스의 내부 구성에 맞춰서 매핑시켜주게 된다. 그 근거는 클래스 내부에 선언한 어노테이션 내용을 보고 매핑하게 된다. 
-  지금의 상황은 Request Body가 Json이라서 자동으로 Json을 파싱해서 매핑해주는게 아니다. Spring 스스로는 이 문자열이 Json인지 XML 인지 알길이 없다. 그걸 판단하는 근거는 Http Header값을 읽어보고 그거에 따라 매핑할 방법을 결정하는 것이다.
-  이제 마지막으로 JSON 문자열을 만들때 선별적으로 만드는 방법에 대해 설명하고자 한다. 요청받은것중에 특정 필드는 JSON 문자열을 만들때 제외하고 싶다는 것이 있었다. 즉 상황에 따라 유동적으로 출력 필드를 제어하고 싶은 것이었다.
- 위에서 언급했던 UnitVO 클래스의 예를 들어 다시 설명하겠다.이 클래스에 name 필드가 있다. 이 필드에는 **@JsonProperty("name")** 어노테이션이 붙어있기 때문에 name 필드 값을 JSON의 name이란 key 값으로 출력하거나 읽어들일 수 있다. 근데 상황에 따라 UnitVO를 이용해서 JSON 문자열을 만들때 name 필드를 제외하고 싶을수가 있다. 이럴 때 하는 방법에 대한 얘기를 하고자 한다.
- JacksonJson 라이브러리에서는 이런 작업때문에 Filter 개념이 도입되는데... 먼저 다음과 같은 Dummy 클래스를 하나 만든다.



```java
import org.codehaus.jackson.map.annotate.JsonFilter;
 
@JsonFilter("filter properties by name")
public class PropertyFilterMixIn {
}
```



-  **@JsonFilter** 어노테이션만 붙어 있고 안에는 아무런 내용이 없어서 Dummy 클래스라고 표현했다. **@JsonFilter** 란 어노테이션을 사용하여 일종의 이름을 부여하게 된다. 여기서는 filter properties by name 이란 이름을 부여했다. 그럼 이것을 어떻게 사용하는지 보자.



```java
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
 
UnitVO obj = service.getUnitVO();
String [] excludeFieldNames = {"name"};
ObjectMapper mapper = new ObjectMapper();
ObjectWriter writer = null;
    if(excludeFieldNames != null){
         mapper.getSerializationConfig().addMixInAnnotations(Object.class, PropertyFilterMixIn.class);   
         FilterProvider filters = new SimpleFilterProvider().addFilter("filter properties by name", SimpleBeanPropertyFilter.serializeAllExcept(excludeFieldNames)); 
     writer = mapper.writer(filters);
}else{
     writer = mapper.writer();
}
String jsonString = writer.writeValueAsString(obj);

```



-  참조할 수 있도록 사용하는 클래스의 package를 넣었다. 소스 설명을 하자면.. Json으로 변환하고자 하는 객체(obj)가 있다. 그리고 여기서 json 출력에서 제외하고 싶은 필드인 name을 String 배열에 넣는다. 
-  그런 다음 ObjectMapper 객체를 만든뒤에 출력에서 제외하고 싶은 필드 이름이 들어가 있는 배열(excludeFieldNames)이 null이 아니면 **addMixInAnnotations** 함수에 위에서 만든 Dummy Filter 클래스를 등록시켜준다. 
-  등록된 Filter에 **excludeFieldNames** 을 설정해서 ObjectWriter 객체를 만들어준뒤 이 ObjectWriter 객체에 JSON으로 변환하고자 하는 객체를 넣어서 JSON문자열을 출력하면 **excludeFieldNames** 배열에 들어가 있는 필드명의 값을 제외한 나머지 JSON으로 출력할수 있는 것들은 출력해주게 된다.



https://madnix.tistory.com/entry/Spring에서-Json으로의-입출력-정리