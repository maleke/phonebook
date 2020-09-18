# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
# تکنولوژی های مورد استفاده در پروژه

پایگاه داده استفاده شده در پروژه : postgresql
صف : rabbitmq
عملیات جستجو : elasticsearch 
فرم ورک استفاده شده : spring boot 2.3.3 می باشد و جهت اتصال به پایگاه داده از spring data jpa استفاده شده است
مستند سازی وب سرویسها: swagger
# نحوه اجرای سرویس ها
جهت ساده سازی اجرای سرویس ها از docker استفاده شده است به گونه ای که با قرار گرفتن در مسیر پروژه و با اجرای دستور زیر میتوان سرویس را بالا آورد. 
docker-compose up -d --remove-orphans
با اجرای این دستور  سرویس ها به ترتیب زیر بالا اورده می شوند: 
سرویس rabbitmq بر روی پورت 15672 بالا می آید. شما قادر هستید با مراجعه به آدرس http://localhost:15672  و با نام کاربری guest و پسورد guest به rabbitmq  وارد شوید.
سرویس elasticsearch برو روی پورت 9200 بالا می آید
سرویس postgres
برنامه phonebook

# معرفی برنامه phonebook

هدف نوشتن برنامه دفترچه تلفن با دو عمل اصلی اضافه کردن کانتکت جدید و عمل جستجوی کانتکت میباشد 

**1. اضافه کردن کانتکت جدید**
جهت فراخوانی این سرویس یک سرویس با استفاده از spring rest نوشته شده است. نام سرویس انجام دهنده این کار ContactController می باشد و آدرس فراخوانی به صورت زیر می باشد:
http://localhost:8001/contacts
در این سرویس باید بتوان مشخصات کاربر وارد شده را در پایگاه داده ذخیره نمود. از آنجاییکه قرار است که لیست repository های آن کاربر در پایگاه داده نیز ذخیره گردد نام کاربری نمی تواند خالی باشد و این فیلد not null در نظر گرفته شده است سایر فیلدها می توانند به صورت اختیاری وارد شوند. 
ورودی و خروجی وب سرویس به صورت یک (Data Transfer Object) با نام ContactDto می باشد . کلاس ContactContrller جهت انجام عملیات ذخیره سازی متد save از کلاس ContactService را فراخوانی می نماید. business plan مربوط به contact  در این لایه قرار دارد.
از آنجایی که قرار است در سرویس بعدی کاربر را جستجو نمود و با توجه به بهینه کردن عملیات جستجو (از آنجایی که باید بتوان جستجو را بر اساس کلیه فیلدها انجام نمود که منجر به پیچیدگی query می شود و کارایی عملیات جستجو کاهش می یابد) از elasticsearch استفاده شده است. بنابراین پس از ذخیره سازی کاربر در پایگاه داده عملیات ذخیره سازی در elastic نیز انجام می گردد.
بنابر نیاز مساله قرار است هنگامی که بخواهیم contact را ذخیره نماییم لیست repository های این کاربر در github را نیز در پایگاه داده ذخیره نمود. فراخوانی این سرویس ممکن است به دلیل قطعی یا کندی سرویس بیرونی منجر به کندی ذخیره سازی contact شود. جهت رفع نیاز مساله مبنی بر بهینه بودن زمان ذخیره سازی contact در پایگاه داده قسمت بدست آوردن لیست repository های کاربر از قسمت ذخیره در پایگاه داده جدا شود به این منظور از تکنولوژی صف rabbitmq استفاده می شود به گونه ای که کار ذخیره سازی در پایگاه داده انجام شده و مشخصات contact در صف قرار داده می شود.
هنگام فرستادن اطلاعات به صف اطلاعات serialize شده و سپس جهت استفاده از آنها می بایست اطلاعات را deserialize نمود جهت انجام این کار کلاس SerializeUtility استفاده شده است. 
**اتصال به github repository**
جهت اتصال به github repository و گرفتن اطلاعات کاربر دو روش وجود دارد:
1. استفاده از api با آدرس https://api.github.com/users/{username}/repos که در ابتدا به جهت گرفتن پاسخ اولیه از این روش استفاده شد. لیکن استفاده از این روش با توجه به اینکه ممکن است بعدا نیاز مساله مبنی بر گرفتن سایر اطلاعات علاوه بر name نیز ممکن است عوض شود و نهایتا maintanance برنامه با مشکل مواجه شود کنار گذاشته شد. مشکل دیگر در استفاده از این روش این است که با فراخوانی سرویس gtihub نتیجه repository ها را به صورت paginated برای ما می آورد که برای حل کردن مساله نیاز به نوشتن کد بیشتر بود در حالیکه این مساله قبلا توسط افراد دیگر توسعه داده شده است و نیازی به نوشتن دوباره سرویس نمی باشد.
2. استفاده از api مربوط به giithub در maven : جهت انجام این کار api مربوطه در فایل pom.xml اضافه گردید و از سرویس RepositoryService مربوط به این api استفاده گرید.
پس از اتصال به github و گرفتن لیست repository های کاربر این اطلاعات در پایگاه داده ذخیره می گردند.

**نکته** 
در صورتی که به هر دلیلی سرویس github به exception ای برخورد کند صف به صورت مکرر این سرویس را فراخوانی می نماید. جهت جلوگیری ازاین مشکل از dead letter queue استفاده گردیده است. همچنین ماکزیمم تعداد دفعات فراخوانی سرویس و فاصله زمانی تکرار فراخوانی ها و سایر ویژگی ها در فایل properties آورده شده است.
**۲- سرویس جستجوی contact ها**
جهت فراخوانی این سرویس یک سرویس با استفاده از spring rest نوشته شده است. نام سرویس انجام دهنده این کار ContactController می باشد و آدرس فراخوانی به صورت زیر می باشد:
http://localhost:8001/contacts/search
در این سرویس نیاز است که contact بر اساس مشخصات وارد شده یافت شود با توجه به این نکته که هرکدام از ویژگی ها می توانند null باشند. از آنجاییکه در این سرویس تک تک فیلدها می توانند null باشند از یک dto جدید به نام  ContactSearchDto استفاده شد چرا که در  ContactDto فرض بر این است که نام کاربری نمی تواند null باشد.
 از آنجاییکه پیدا نمودن contact ها بر اساس کلیه مشخصات و انتخابی بودن این مشخصات منجر به پیچیدگی query و نهایتا کاهش performance گردند تصمیم بر آن شد از elasticsearch استفاده شود تا این اطلاعات به سرعت بازیابی گردند. جهت انجام این کار contact entity را با استفاده از Document@ به عنوان یک موجودیت قابل index معرفی می نماییم و تک تک property ها را به عنوان field معرفی می نماییم . جهت انجام جستجو قبل از جستجوی هر فیلد از null نبودن فیلد اطمینان حاصل می شود. 
جهت نوشتن query مورد نظر از  NativeSearchQueryBuilder استفاده شده است

# نحوه تست برنامه
جهت تست برنامه می توان از postman استفاده نمود. در این برنامه جهت  api documentation از swagger استفاده شده است. جهت مشاهده ورودی وب سرویس ها می توانید از آدرس زیر استفاده نمایید.
http://localhost:8001/swagger-ui/index.html
# نحوه اجرای برنامه

جهت اجرای برنامه فایل run.sh موجود در برنامه را اجرا نمایید
**نکات**
با توجه به اینکه برنامه در محیط لینوکس نوشته شده است مسیر log در فایل logback-spring.xml در /home/fatemeh/phonebook/logs می باشد که در صورتیکه در محیط ویندوز اجرا می نمایید مسیر فایل را تغییر دهید.


