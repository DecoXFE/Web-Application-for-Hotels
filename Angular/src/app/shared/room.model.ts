export enum RoomType{
    SINGLE = 'SINGLE',
    DOUBLE = 'DOUBLE',
    SUITE = 'SUITE'
}

export interface Room{
    id : Number;
    roomNumber : String;
    roomType : RoomType;
    available : boolean;
}