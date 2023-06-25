package coms309;

import com.fasterxml.jackson.annotation.JsonProperty;

class Calculator {
    @JsonProperty
    int s1;
    @JsonProperty
    int s2;
    @JsonProperty
    int t1;

    public String mainCalc(){
        int s3;
        if(t1 == 1){
            s3 = s1 + s2;
            return "ADDITION OF " +  s1 + " AND " + s2 + " IS " + s3;
        }
        else if(t1 == 2){
            s3 = s1 - s2;
            return "SUBTRACTION OF " +  s1 + " AND " + s2 + " IS " + s3;
        }

        else if(t1 == 3){
            s3 = s1 * s2;
            return "MULTIPLICATION OF " +  s1 + " AND " + s2 + " IS " + s3;
        }

        else if(t1 == 4){
            s3 = s1 / s2;
            return "DIVISION OF " +  s1 + " AND " + s2 + " IS " + s3;
        }

        else if(t1 == 5){
            s3 = s1 % s2;
            return "MODULO OF " +  s1 + " AND " + s2 + " IS " + s3;
        }
        else{
            return "ERROR, INPUT NOT WITHIN RANGE";
        }
    }
}