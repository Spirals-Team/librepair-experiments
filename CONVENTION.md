|- Code format -|

ภาษาหลักที่ใช้ในการพัฒนา : Java

Format ตัวแปร ใช้เป็น camelCase : Upper Camel Case สำหรับตั้งชื่อ Class, Lower สำหรับชื่อตัวแปรและ method

Refactor Code ทุกครั้งที่ทำเสร็จ

เวลาเขียน Class, Method ให้ Comment ด้วยว่า มันทำอะไรในโค้ดแบบคร่าวๆ กันอ่านโค้ดไม่รู้เรื่อง

การตั้งชื่อตัวแปร ให้อ่านรู้เรื่องมีความหมาย

ลบ Code ที่ไม่ได้ใช้งานออก (Unused code)

ภายในเงื่อนไข loop ให้เว้นวรรคระหว่าง Operator และ Operand กับ semicolon

สิ้นสุด loop ไม่ต้องเว้นบรรทัด

เขียน statement ละ 1 บรรทัด

หาก statement ยาวเกิน 120 ตัวอักษรและให้ขึ้นบรรทัดใหม่ และ Indent ให้ตรงกัน

เว้น 1 บรรทัดระหว่าง method

เว้น 1 บรรทัดระหว่าง constructor

ประกาศตัวแปร attribute ไว้ล่าง constructor โดยเว้นระยะห่างไว้ 1 บรรทัด

Constructor เปล่า (default) เขียนให้อยู่ในบรรทัดเดียว

Constructor ที่มีการรับค่าให้เขียน statement ละ 1 บรรทัด

return : ไม่ต้องเว้นบรรทัดระหว่าง return

ภายในเงื่อนไข if เว้นวรรคระหว่างตัวดำเนินการ

ถ้ามี เงื่อนไขอื่นต่อกันให้เขียนติดกับปีกกาปิดของเงื่อนไขล่าสุด

ตัวอย่าง Code
```
Class ConventionNote(){
	//This is our convention
	ConventionNote(){}
  
	ConventionNote(String args){
            this.args = "HelloWorld";
	}

  	private int countVowel;

	public int countVowels(String letter){
        //This method return total vowel found in letter
            return countVowel;
	}
}
```
