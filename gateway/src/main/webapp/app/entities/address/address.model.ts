export class Address {
    constructor(
        public id?: number,
        public street1?: string,
        public street2?: string,
        public street3?: string,
        public city?: string,
        public province?: string,
        public postalCode?: string,
        public country?: string,
    ) {
    }
}
