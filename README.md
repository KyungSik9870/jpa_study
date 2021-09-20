
#*JPA (Java Persistence API)*
## Java 의 ORM 기술 표준.

### ORM 이란 ?? 🤔
* ORM(Object Relational Mapping) : 객체와 관계형 데이터베이스를 매핑

ORM Framework 는 객체와 테이블을 매핑해서 패러다임의 불일치를 개발자 대신 해결해준다.

<img width="500" alt="jpa" src="https://user-images.githubusercontent.com/54000031/133955794-caba8f8b-895f-4f23-803e-8714ea3e8cef.png">

예를들어 프레임워크를 사용하면, 객체를 데이터베이스에 저장할때  
개발자가 직접 insert 문을 작성하는 것이 아니라  
ORM 프레임워크가 적절한 insert 문을 작성해 데이터베이스에 저장해준다.



<img width="600" alt="jpa_save" src="https://user-images.githubusercontent.com/54000031/133955828-297e6309-732a-4eb6-815a-29d48ac51e2d.png">

위의 사진이 데이터를 저장하는 과정이다.


<img width="600" alt="jpa_select" src="https://user-images.githubusercontent.com/54000031/133955845-59a31323-2487-451e-ad83-571341e8e285.png">


위는 데이터를 조회해오는 과정이다.

ORM 프레임워크는 패러다임의 불일치 문제들을 해결해주고, 흔히 이야기하는 CRUD 의 기능을 강력히 제공해준다.

그 중 Hibernate 프레임워크는 굉장히 성숙하고 java 에서 ORM 을 한다고 했을때  
가장 많이 사용하는 프레임워크이다.  
'흔히 JPA 를 공부한다 = Hibernate 를 공부한다'  
라고 생각할 정도로 많이 사용한다.

## 👍 JPA 를 사용하는 이유?
#### * 생산성  
  
    JPA 를 사용하면 자바 컬렉션에 객체를 저장하듯이 데이터의 저장을 할 수 있다.  
    조회의 경우도 마찬가지다. 흔히 우리가 얘기하는 CRUD의 과정을 개발자가  
    쿼리를 작성하지 않고 처리할 수 있다.
    

#### * 유지보수
    
    SQL 을 직접 다루게 되면, 엔티티에 필드를 하나만 추가하더라도 모든 쿼리를 다 수정해야한다.  
    하지만 JPA 를 사용하게 되면 이런 과정을 JPA 가 대신 처리해주므로  
    개발자가 수정해야할 소스가 줄어든다. 이는 실수가 줄어드는 효과도 가진다.
  

#### * 패러다임의 불일치 해결

    관계형 데이터베이스를 객체의 형태로 다루면서 나타나는 페러다임의 불일치를 연관관계의 매핑으로  
    해결 할 수 있다. 
  

#### * 성능

    JPA 는 다양한 성능 최적화를 제공한다. 특히 쿼리의 실행계획 최적화와 개발자가 코드내에서 할 수 있는  
    다양한 성능 최적화를 다룰 수 있어 장점이 있다.
  

#### * 데이터 접근 추상화와 벤더 독립성

    벤더마다 쿼리작성법이 다른 경우를 많이 봐왔다. 단순히 ROWNUM 을 구하는 것 조차도 DB 벤더마다 다르다.  
    페이징처리와 같은 경우도 마찬가지이고, 전회사에서는 Postgres 에 완전히 종속적인 코드를 만들었는데,  
    고객사가 Oracle 로 변경요청을 했고, 단순히 벤더를 바꾸는 요청이지만 공수로 1년을 불러  
    고객사가 요청을 포기하게 만들었다.  
  
    JPA는 Dialect 라는 추상화된 데이터 접근 계층을 제공해서 특정 데이터베이스에 종속되지 않도록 한다.  
    위와 같이 요청을 받으면, 단순히 프로퍼티 파일에서 사용할 DB를 import 해주는 것으로 끝이난다.
  




## ✨✨ 영속성 관리

###영속성 컨텍스트 : 엔티티를 영구 저장하는 환경

>엔티티 매니저로 엔티티를 저장하거나 조회하면  
>엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관, 관리한다.


### 🔸 영속성 컨텍스트의 특징

###엔티티 매니저 팩토리와 엔티티 매니저

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("");

    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Member newMember = new Member();
    newMember.setId("member2");
    newMember.setName("경식");
    em.persist(newMember);

    Member member = em.find(Member.class, "member1");
    member.setName("굥식");
    
    transaction.commit();

***

Persistence.createEntityMangerFactory("jpa");  
META-INF/persistence.xml 에 있는 정보를 바탕으로 EntityManagerFactory 를 생성

엔티티 매니저 팩토리는 여러 스레드가 동시에 접근해도 안전.  
엔티티 매니저는 여러 스레드가 동시 접근하면 동시성 문제가 발생. 스레드 간에 공유 X

<img width="500" alt="entity_factory" src="https://user-images.githubusercontent.com/54000031/133957266-4de887aa-df49-40ab-926b-4a72b95d9395.png">


위의 그림을 보면, EntityManagerFactory 가 다수의 엔티티 매니저를 생성한다.  
엔티티매니저는 데이터 베이스 커넥션을 사용하지 않고 있다가, 꼭 필요한 시점에 커넥션을 얻는다.  
보통 트랜잭션을 시작할 때 커넥션을 획득.    


### 🔄 엔티티의 생명주기(상태)
생명주기라고 표현하지만, 상태로 이해하는게 더 쉬워서 상태라고도 표기를 하였습니다.

>1. 비영속(New) : 영속성 컨텍스트와 전혀 관계가 없는 상태  
>2. 영속(managed) : 영속성 컨텍스트에 저장된 상태  
>3. 준영속(detached) : 영속성 컨텍스트에 저장이 되었다가 분리된 상태  
>4. 삭제(removed) : 영속성 컨텍스트에서 삭제된 상태  


<img width="500" alt="persistence_context" src="https://user-images.githubusercontent.com/54000031/133957291-fc6986bb-817d-4b60-9778-10b2607e594f.png">


####* 영속성 컨텍스트와 식별자 값
    
    영속성 컨텍스트는 엔티티를 식별자 값으로 구분.   
    따라서 영속 상태는 식별자 값이 반드시 있어야한다.

####* 영속성 컨텍스트와 데이터베이스 저장

    영속성 컨텍스트에 엔티티를 저장하면, JPA 는 트랜잭션을 커밋하는 순간 영속성 컨택스트에 저장된  
    엔티티를 데이터베이스에 반영하고, 이를 flush 라고 한다.  

####* 영속성 컨텍스트가 엔티티를 관리했을때 얻는 장점

    1. 1차 캐시  
    2. 동일성 보장  
    3. 트랙잭션을 지원하는 쓰기 지연  
    4. 변경 감지  
    5. 지연 로딩  

## ✨ 1차 캐시

    Member member = new Memner();
    member.setId("member1");
    member.setUsername("name1");

    em.persist(member);


<img width="450" alt="first_cache" src="https://user-images.githubusercontent.com/54000031/133957313-3a53021f-3825-4ccf-9fc3-6a76ce24edd0.png">


    Member member = em.find(Member.class, "member1");


<img width="450" alt="find_first_cache" src="https://user-images.githubusercontent.com/54000031/133957341-4c37ec62-66fa-4e67-a4ad-7996dfe579ae.png">


그렇다면 1차 캐시에 없다면??

    Member member2 = em.find(Member.class, "member2");


<img width="550" alt="find_db_save_cache" src="https://user-images.githubusercontent.com/54000031/133957357-c168452b-5dc0-4484-80c1-e9f46e4626b4.png">



###영속 엔티티의 동일성 보장

    Member a = em.find(Member.class, "member1");
    Member b = em.find(Member.class, "member1");

    String a1 = "abc";
    String a2 = "abc";

    System.out.println( a1 == a2 );
    System.out.println( a == b );

여기서 a 와 b 의 동일성은 보장될까??  

몇번을 반복해도 1차 캐시에 있는 같은 인스턴스를 반환하므로 둘은 항상 동일하다.  

이와 같이 DB에 다녀오지 않고 캐시안의 값을 쓰는 것에서 성능성의 이점이 있고,  
엔티티의 동일성까지 보장할 수 있다.  


### 엔티티 등록

    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();

    transaction.begin();

    Member memberA = new Member();
    memberA.setId("member1");
    memberA.setUsername("name1");

    ... 

    em.persist(memberA);
    em.persist(memberB);

    transaction.commit();


위와 같이 코드를 작성했을때 영속성 컨텍스트에서 어떤일이 발생하는지 보면


<img width="550" alt="persist_first" src="https://user-images.githubusercontent.com/54000031/133957388-3f76e540-0028-4e0f-986c-17e04081b695.png">
<img width="550" alt="persist_second" src="https://user-images.githubusercontent.com/54000031/133957394-b8c2075b-f45b-4539-981d-a23a5aa9e780.png">
<img width="550" alt="persist_third" src="https://user-images.githubusercontent.com/54000031/133957396-605c4ce7-f399-4d06-946a-b73a3b558694.png">


트랜잭션이 커밋되는 순간 쿼리가 DB에 날라가 반영이 된다. => flush 한다.  
flush = 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화  

이를 잘 생각해보면 쓰기 지연이 가능하다.

트랙잰션이 커밋, 즉 flush 가 되어야 실제 DB에 반영되기 때문에  
영속성 컨텍스트에 원하는 만큼 엔티티 등록을 먼저 해놓고  
필요한 순간에 커밋하여 모아둔 등록쿼리를 한번에 날릴 수 있다.  


## ✨ 변경감지

기존의 쿼리 작성 방식은 update 문을 통해 데이터를 변경해왔다.  

이것의 문제점은 수정쿼리가 프로그램의 규모에 따라 방대하게 늘어나고 관리가 어렵다.  
또한 비즈니스 로직을 작성/분석 하는 중에 계속 쿼리를 확인해야하고  
SQL 문에 의존적이게 된다.  

내 생각에 이방식의 가장 큰 문제점은 수정쿼리가 많아졌을때, 특히 개발버전에 따라 엔티티의  
컬럼들이 변경됐을때  하나하나 문제가 될만한 쿼리를 찾아서 전부다 수정해야됨에 있다.  

이런 것들을 놓쳐서 고객사에 쿼리만 다시 배포한 경험을 나도 많이했고, 정말 많이 봐왔다.  

###그렇다면, JPA 는 🧐 ??

    Member memberA = em.find(Member.class, "memberA");
    
    memberA.setUsername("경식");
    memberA.setAge(29);

    transaction.commit();

위와 같은 코드를 작성하면 어떻게 될까?


<img width="550" alt="detect_change" src="https://user-images.githubusercontent.com/54000031/133957425-05d5507d-a8f7-4021-93ed-207ee40480a1.png">


>1. 트랜잭션을 커밋하면 엔티티 매니저 내부에서 플러시 호출
> 
> 
>2. 엔티티와 스탭샷을 비교해서 변경된 엔티티 찾음
        * 스냅샷 : 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장하는데 이를 스냅샷이라고 한다.
> 
> 
>3. 변경된 엔티티가 있으면 수정 쿼리를 생성해서 쓰기 지연 SQL 저장소에 보관
> 
> 
>4. 쓰기 지연 저장소의 SQL 을 데이터 베이스에 보낸다
> 
> 
>5. 데이터 베이스 트랜잭션 커밋


* 변경감지는 영속성 컨텍스트가 관리하는 영속 상태의 엔티티에만 적용된다.
  

## 🔸 Flush  
### Flush : 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영한다.  
영속성 컨텍스트의 변경 내용을 데이터베이스와 동기화  

#### ✹ Flush 가 일어나는 경우
1. em.flush() 호출하는 경우  
2. 트랜잭션 커밋  
3. JPQL or Criteria 쿼리 실행 시 (나중에) 


#### ✹ Flush mode 옵션
* FlushModeType.AUTO : 커밋이나 쿼리를 실행할 때 플러시 (default)  
* FlushModeType.COMMIT : 커밋할 때만 플러시  


### ✹ 준영속 
영속성 컨텍스트에서 엔티티가 분리된 상태 (detach)  

#### * __특징__  
> 1. 거의 비영속 상태에 가까움  
>     → 영속성 컨택스트가 관리하지 않음.
>
>     1차 캐시 / 쓰기 지연 / 변경감지 / 지연로딩 과 같은 기능을 제공안함.
>
> 
> 2. 식별자 값을 가지고 있다  
> → 이미 한번 영속 상태였으므로, 식별자 값을 가지고 있음.
> 
> 
>3. 지연 로딩을 할 수 없다.  
>  → 지연 로딩(Lazy Loading) 실제 객체 대신에 프록시 객체를 로딩해두고 사용.  
>    준영속 상태일때는 영속성 컨텍스트가 관리하지 않아, 지연로딩시 문제가 생김  

### ✹ 병합 merge()

준영속 상태의 엔티티를 다시 영속 상태로 변환
새로운 영속 상태의 엔티티를 반환  


<img width="550" alt="detach_merge" src="https://user-images.githubusercontent.com/54000031/133957436-d2386762-1dbf-45da-964b-e14339a16bd2.png">



# ✨✨ 엔티티 매핑

## @Entity

JPA 를 사용해서 테이블과 매핑할 클래스에 붙여주는 어노테이션.

* 엔티티는 기본 생성자는 필수로 작성
* final 클래스, enum, interface, inner 클래스는 사용할 수 없다
* 저장할 필드에 final 을 사용하면 안된다

## @Table

...

어노테이션에 관한 것은 여기서 기술하지는 않겠습니다.  
필요한 어노테이션, 메서드 등은 찾아서 공부해서 알맞게 사용하는 것이 기억에 오래 남는다고 생각함.


# ✨✨ 연관관계 매핑 😈

## 사람들이 가장 어려워하고, 포기하는 연관관계 매핑 😈

기존의 RDB는 테이블끼리의 외래키로 매핑하는 형태  
JPA 는 이걸 객체의 참조로 해결해야 하기 때문에, 앞서 말했던 패러다임의 불일치가 오고  
이를 해결하기 위한 연관관계 매핑 또한 난이도가 높다.  
난이도를 따지기 전에 머리로 패러다임의 불일치를 인정하고 이해해야한다.  


>* 방향 : 단방향 / 양방향 이 존재. RDB는 외래키 하나를 가지고 양방향접근이 가능하기 때문에, 단방향은 객체간에만 존재.
>* 다중성 : 다대일, 일대다, 일대일, 다대다 다중성이 있다.
>* 연관관계의 주인 : 이게 겁나 헷갈림

## 🔸 단방향 연관관계


<img width="550" alt="n1_mapping" src="https://user-images.githubusercontent.com/54000031/133957446-c20d5ad2-db19-45e0-a67a-22ad8f2858ff.png">


이 그림을 해석못한다면 공부하시길...

JPA 를 하면서 가장 많이 하는 오해가, 이제 쿼리작성할 일은 없을테니  
자바공부 빡쎄게 하고 객체참조열심히 해서 프로그래밍 해야겠다 !!!! 이거임


패러다임의 불일치를 인정하고 이해하면서 객체간의 관계를 짜야하므로  
오히려 RDB 에 대해서 그전보다 더 깊고 자세히 알아야한다.  

따라서 저런 테이블 관계를 도식화한 그림을 이해하지 못한다면 JPA 공부는 지옥이 될거임.

---

###  @ManyToOne
* optional : false 로 설정하면 연관된 엔티티가 항상있어야함
* fetch : fetch 전략 설정. EAGER, LAZY 가 있다. 나중에 설명
* cascade : 영속성 전이 기능. RDB 를 다뤄봤으면 눈에 익는 키워드여야함
* targetEntity

### @JoinColumn
* name : 매핑할 외래 키 이름. 필드명_참조하는 테이블의 기본키 컬럼명
* referencedColumnName 
* foreignKey : 외래 키 제약조건을 직접 지정. DDL 생성시 사용


## 🔸 양방향 연관관계


<img width="550" alt="bothside_mapping" src="https://user-images.githubusercontent.com/54000031/133957463-6e8fdee1-fe63-4dd4-938e-a7d2f45c4a71.png">



---

## @OneToMany 는 OK, 근데 mappedBy .....?? 🧐❓

테이블은 외래 키 하나로 두 테이블의 연관관계를 관리.

엔티티를 단뱡향으로 매핑했을 때는 사용하는 참조도 한개이다.  
이 참조로 외래키를 관리하면 되니깐 mappedBy 를 사용하지 않아도 된다.

그런데 양방향으로 매핑하는 순간 회원 → 팀, 팀 → 회원 이렇게 두군데서 서로를 참조한다.  

그러면 객체의 참조는 두개인데, 외래 키는 하나이다. 
→ 패러다임의 불일치다. 객체는 RDB 와 다름. 

따라서 두 객체 연관관계 중 하나를 정해서 테이블의 외래키를 관리해야한다.  
### 이 관리하는 친구를 *__"연관관계의 주인"__* 이라고 한다.

###  연관관계의 주인만이 외래키를 관리(등록, 수정, 삭제) 할 수 있다. 즉, 주인이 아닌 객체는 읽기만 할 수 있다.

주인은 mappedBy 속성을 사용하지 않는다.   
주인이 아니면 mappedBy 속성을 사용해서 연관관계의 주인을 지정해야 한다.  

### 🧐 그러면 누가 주인으로 갈지 어떻게 정하지??

생각을 조금 해보자.  
위의 그림을 생각해보면, MEMBER 테이블에 물리적으로 TEAM_ㅈID(외래키) 라는 컬림이 존재한다.

따라서 물리적으로 접근이 가능한 MEMBER 테이블이 주인이 되어야한다.  

하지만 조금 더 기억하기 쉽게 이야기를 하자면, ManyToOne 이 주인이 되고  
저는 *"쪽수를 못당한다...많은쪽이 주인이다..."*  
이렇게 외웠습니다.  조금 없어보이지만 헷갈리지는 않는...ㅎㅎ


---


[출처 - 자바 ORM 표준 JPA 프로그래밍 (저자 김영한)]


