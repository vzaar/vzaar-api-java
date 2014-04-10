vzaar API Java client
---
vzaar API client for Java developers.

---

>vzaar is the go to video hosting platform for business. Affordable, customizable and secure. Leverage the power of online video and enable commerce with vzaar.

----

###Examples

```java
import com.vzaar.api;
```

Now you can create your vzaar client instance:

```java
Vzaar api = new Vzaar("VZAAR_USERNAME", "VZAAR_TOKEN");
```

In order to use vzaar API, you need to have a valid user name and API token that you can get from your vzaar dashboard at [http://vzaar.com/settings/third_party](http://vzaar.com/settings/third_party).

The very next thing you would want to do is to check if your account actually works and operational and you can do it by simple calling _whoAmI()_:

```java
System.out.println((api.whoAmI());
```
