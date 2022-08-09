import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
// annotation의 중복사용이 가능하도록 Container 어노테이션 타입을 선언 (여러개의 어노테이션을 감싸는 역할)
public @interface ChickenContainer {
	Chicken[] value(); // chicken을 감싸고 있을 어노테이션 배열
}
