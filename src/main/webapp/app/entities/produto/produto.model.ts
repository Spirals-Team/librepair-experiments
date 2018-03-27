import { BaseEntity } from './../../shared';

export class Produto implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public dataCadastro?: any,
        public descricao?: string,
    ) {
    }
}
