export enum UserGender{
    Male,
    Female,
    Other
}

export class SignUpInfo{
    
    username: String;
    password: String;
    name: String;
    age: Number;
    gender: UserGender;
    town: String;
    balance: Number;

    constructor(name: String,
        username: String,
        password: String,
        age: Number,
        gender: UserGender,
        town: String,
        balance: Number)
        {
            this.name=name;
            this.username=username;
            this.password=password;
            this.age=age;
            this.gender=gender;
            this.town=town;
            this.balance=balance;
        }
    
       
}