export enum RoomType{
    SINGLE = 'SINGLE',
    DOUBLE = 'DOUBLE',
    SUITE = 'SUITE'
}

export interface Room{
    roomNumber : String;
    roomType : RoomType;
    avalible : boolean;
}