function heartStats(type, rate, age){
  if(type = "rest"){
    return restheartStats(rate, age);
  }
  return sportHeartStats(rate, age);
}
function restHeartStats(rate, age){
  if(age < 2){
    if(rate <= 100){
      return "bellow avarage";
    }
    if(rate >= 170){
      return "above avarage";
    }
  }
  else if(age > 1 && age <= 11){
    if(rate <= 60){
      return "bellow avarage";
    }
    if(rate >= 110){
      return "above avarage";
    }
  }
  else if(age > 11){
    if(rate < 40){
      return "dangerous";
    }
    if(rate < 60 && rate >= 40){
      return "athlete";
    }
    if(rate > 90){
      return "above avarage";
    }
  }
  return "avarage";
}
function sportHeartStats(rate, age){
  maxRate = 220 - age;
  upper = maxRate * 0.85;
  lower = maxRate * 0.5
  if(rate <= lower){
    return "bellow avarage";
  }
  if(rate >= upper){
    return "above avarage";
  }
  return "avarage";
}
