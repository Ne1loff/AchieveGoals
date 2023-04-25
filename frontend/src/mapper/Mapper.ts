import {getProperty, hasProperty, isTypeOf, setProperty} from "../utils/objects";

interface SpecificMapping<T1> {
    key: string,
    mapping: <T2 extends any>(value: T1) => T2
}

interface Mapper<E extends object, D extends object> {
    toDto(entity: E): D

    toEntity(dto: D): E

    toDtos(entities: E[]): D[]

    toEntities(dtos: D[]): E[]
}

abstract class AbstractMapper<E extends object, D extends object> implements Mapper<E, D> {
    abstract toDto(entity: E): D;

    abstract toEntity(dto: D): E;

    abstract toDtos(entities: E[]): D[];

    abstract toEntities(dtos: D[]): E[];

    protected _toDto(entity: E, dto: D, specificMappings: SpecificMapping<E>[] = []): D {
        const keys = Object.keys(dto) as Array<keyof D>;

        keys.forEach((key) => {
            if (hasProperty(entity, key)) {
                let value = getProperty(entity, key);
                const gettingValue = getProperty(dto, key);

                if (specificMappings.length > 0) {
                    const mapping = specificMappings.find(it => it.key === key);
                    if (mapping)
                        setProperty(dto, key, mapping.mapping<typeof gettingValue>(entity));
                    else if (isTypeOf<typeof gettingValue>(value))
                        setProperty(dto, key, value);
                    else
                        throw Error();
                } else if (isTypeOf<typeof gettingValue>(value)) {
                    setProperty(dto, key, value);
                }
            }
        });

        return dto;
    }

    protected _toEntity(dto: D, entity: E, specificMappings: SpecificMapping<D>[] = []): E {
        const keys = Object.keys(entity) as Array<keyof E>;

        keys.forEach((key) => {
            if (hasProperty(dto, key)) {
                let value = getProperty(dto, key);
                const gettingValue = getProperty(entity, key);

                if (specificMappings.length > 0) {
                    const mapping = specificMappings.find(it => it.key === key);
                    if (mapping)
                        setProperty(entity, key, mapping.mapping<typeof gettingValue>(dto));
                    else if (isTypeOf<typeof gettingValue>(value))
                        setProperty(entity, key, value);
                    else
                        throw Error();
                } else if (isTypeOf<typeof gettingValue>(value)) {
                    setProperty(entity, key, value);
                }
            }
        });

        return entity;
    }
}

export {AbstractMapper};
export type {Mapper, SpecificMapping};
