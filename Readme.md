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

علت افزودن dto  برای search
با توجه به اینکه ورودی های search create با یکدیگر یکسان هستند به نظر می رسد که باید از dto یکسانی برای آنها استفاده نمود لیکن با توجه به اینکه در هنگام ذخیره کردن وارد کردن نام اجباری است ولی در search این فیلد می تواند خالی باشد تصیم بر ان شد که از یک dto جدید استفاده شد. ارجحیت بر آن شد که از inheritance در این مورد استفاده نشود
