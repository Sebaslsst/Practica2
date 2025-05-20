import { _getPropertyModel as _getPropertyModel_1, BooleanModel as BooleanModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, NumberModel as NumberModel_1, ObjectModel as ObjectModel_1, StringModel as StringModel_1 } from "@vaadin/hilla-lit-form";
import type Expresion_1 from "./Expresion.js";
class ExpresionModel<T extends Expresion_1 = Expresion_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(ExpresionModel);
    get id(): NumberModel_1 {
        return this[_getPropertyModel_1]("id", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get expresion(): StringModel_1 {
        return this[_getPropertyModel_1]("expresion", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.lang.String" } }));
    }
    get isCorrecto(): BooleanModel_1 {
        return this[_getPropertyModel_1]("isCorrecto", (parent, key) => new BooleanModel_1(parent, key, true, { meta: { javaType: "java.lang.Boolean" } }));
    }
}
export default ExpresionModel;
