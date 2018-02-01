#!/bin/sh

# Clean output directory, excluding customised files
keep="src/main/java/com/mailosaur/MailosaurClient.java"

for f in src/main/java/com/mailosaur/*
do
  if [[ ! " ${keep[@]} " =~ " ${f} " ]]; then
    rm -rf "$f"
  fi
done

# Rebuild generated code
autorest

# Rename MailosaurErrorException to MailosaurException
for f in `find "src/main/java/com/mailosaur" -type f -name "*.java"`
do
  sed -i "" "s/models\.MailosaurErrorException/MailosaurErrorException/" "$f"
  sed -i "" "s/MailosaurErrorException/MailosaurException/" "$f"
done
mv src/main/java/com/mailosaur/models/MailosaurErrorException.java src/main/java/com/mailosaur/MailosaurException.java
sed -i "" "s/\.models//" src/main/java/com/mailosaur/MailosaurException.java
sed -i '' '/retrofit2\.Response/ a\
  import com.mailosaur.models.MailosaurError;\
  ' src/main/java/com/mailosaur/MailosaurException.java
sed -i '' 's/(MailosaurError)\ super\.body()/null/' src/main/java/com/mailosaur/MailosaurException.java
sed -i '' -e '$d' src/main/java/com/mailosaur/MailosaurException.java
echo "
    public MailosaurError mailosaurError() {
        return (MailosaurError) super.body();
    }
}
" >> src/main/java/com/mailosaur/MailosaurException.java

# Inject generateEmailAddress method to Servers (interface & implementation)
sed -i '' '/java\.util\.List/ a\
  import java.util.Random;\
  ' src/main/java/com/mailosaur/implementation/ServersImpl.java
sed -i '' -e '$d' src/main/java/com/mailosaur/Servers.java
sed -i '' -e '$d' src/main/java/com/mailosaur/implementation/ServersImpl.java

echo "    String generateEmailAddress(String server);
}" >> src/main/java/com/mailosaur/Servers.java

echo "    private Random random = new Random();

    public String generateEmailAddress(String server) {
        String host = System.getenv(\"MAILOSAUR_SMTP_HOST\");
        host = (host != null) ? host : \"mailosaur.io\";
        String randomString = String.valueOf(random.nextInt(10000000));
        return String.format(\"%s.%s@%s\", randomString, server, host);    	
    }
}" >> src/main/java/com/mailosaur/implementation/ServersImpl.java