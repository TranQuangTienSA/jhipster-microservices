export class BusinessUnit {
    constructor(
        public id?: number,
        public name?: string,
        public division?: string,
        public website?: string,
        public mainPhone?: string,
        public otherPhone?: string,
        public fax?: string,
        public email?: string,
        public billToAddressId?: number,
        public shipToAddressId?: number,
        public parentId?: number,
    ) {
    }
}
