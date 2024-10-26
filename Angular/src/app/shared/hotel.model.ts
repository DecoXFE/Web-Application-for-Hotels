import { Room } from "./room.model";

export interface Address{
    streetKind : String;
    streetName : String;
    number : Number;
    postCode : String;
    otherInfo : String;
}

export interface Hotel{
    id : Number;
    name : String;
    address : Address;
    roomCollection: Room[]
}