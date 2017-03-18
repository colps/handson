package com.example.java8;


public class StaticAndDefault {

    public interface Person {

        String getId();
        // default method
        default String getName() {
            return "Person";
        }

        // you can now have static methods as well
        static void print(){
            System.out.println("Person.print");
        }
    }

    public interface Named {
        default String getName(){
            return "";
        }
    }

   public interface Unique {
      default String getId(){
          return "";
      }
   }

   public class Student implements Person, Unique, Named{
        // If 1 default and 1 abstract (getId) methods are inherited,
       // the class must overide the method
       @Override
       public String getId() {
           return "id";
       }

       // 2 default methods. need to overide explicitly. To delegate to interface
       // methods used InterfaceName.super
       // Note- Once the method is overidden in a class (Student in this case), you can't use the
       // InterfaceName.super in the child classes of Student.
       @Override
       public String getName() {
           if(true){
               return Named.super.getName();
           } else {
               return Person.super.getName();
           }
       }
   }




}
