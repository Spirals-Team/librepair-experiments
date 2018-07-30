# Gamified Library Management System (GLMS)
# REST API 
## library

{
  "book": [
    {
      "bookId": 0,
      "bookname": "JAVA8"
    },
    {
      "bookId": 0,
      "bookname": "Angular 6"
    }
  ],
  "bookcatId": 0,
  "name": "CSE"
}

{
  "book": [
    {
      "bookId": 0,
      "bookname": "Robotics"
    },
    {
      "bookId": 0,
      "bookname": "SpaceX"
    }
  ],
  "bookcatId": 0,
  "name": "Mech"
}

## Search books

by ID
by name
by category(Department)

## User table
userid, name, role

## BookRequest table:
bookreqid,bookid, userid, status (Q/I/R)
### Return the book
Once the user mark the book as Return Scheduler will issue the book to next user
### BookReview table:
bookreviewid,bookid, userid,review
### Likereview
bookreviewid,userid,likestatus,vote(U/D),comment
### points & badge calculation
get all records from BookRequest table

BOOKREQID   BOOK   CATEGORY   STATUS   USERID   
1 Book1inCat1 Cat1 D 1 
2 Book2inCat1 Cat1 D 1 
3 Book1inCat2 Cat2 D 1 
4 Book1inCat1 Cat1 D 2 
5 Book1inCat3 Cat3 D 1 
6 Book1inCat1 Cat1 D 3 
7 Book1inCat2 Cat2 D 4 

## userid 1 details

1 1 Book1inCat1 Cat1D 
2 1 Book2inCat1 Cat1D 
3 1 Book1inCat2 Cat2D 
5 1 Book1inCat3 Cat3D 
## expected output

No. of distinct books:4 
Distinct category:[Cat1, Cat2, Cat3] 
Category wise count:{Cat3=1, Cat2=1, Cat1=2} 
More than one book taken from Category:{Cat1=2} 
## BookRequest controller
@RequestMapping("/userwise/{userId}") 
    public void userwise(@PathVariable Long userId) { 
        List<BookRequest> lstBookRequest = bookRequestRepository.findAll(); 
        lstBookRequest.stream().filter(x -> userId == (x.getUserid())).forEach(System.out::println); 
        
        long l = lstBookRequest.stream().filter(x -> userId == (x.getUserid())).distinct().count(); 
        System.out.println("No. of distinct books:" + l); 
        List<String> categories = lstBookRequest.stream().filter(x -> userId == (x.getUserid())) 
                .filter(distinctByKey(BookRequest::getCategory)).map(x -> x.getCategory()).collect(Collectors.toList()); 
        System.out.println("Distinct category:" + categories); 

        Map<String, Long> strmap = lstBookRequest.stream().filter(x -> userId == (x.getUserid())) 
                .collect(Collectors.groupingBy(BookRequest::getCategory, Collectors.counting())); 
        System.out.println("Category wise count:"+strmap); 

        Map<Object, Object> collect = strmap.entrySet().stream().filter(map -> map.getValue() > 1) 
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue())); 
        System.out.println("More than one book taken from Category:"+collect); 
} 




# Angular




