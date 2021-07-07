/* 
    Arquivo principal para desenvolvimento do protótipo de fechadura inteligente, utilizando arquitetura web of things, com auxílio da biblioteca de WebThings.
*/

//imports de bibliotecas para relacionamento com things
import Ajv from 'ajv';
import Property from './property';
import Event from './event';
import Action from './action';
import { AnyType, Link, Subscriber } from './types';

const ajv = new Ajv();

/**
 * A Web Thing.
 */
 class Thing {
    private id: string;
  
    private title: string;
  
    private type: string[];
  
    private context: string;
  
    private description: string;
  
    private properties: { [name: string]: Property };
  
    private availableActions: {
      [actionName: string]: {
        metadata: Action.ActionMetadata;
        class: Action.ActionTypeClass;
      };
    };
  
    private availableEvents: {
      [name: string]: {
        metadata: Event.EventMetadata;
        subscribers: Set<Subscriber>;
      };
    };

    private availableEvents: {
        [name: string]: {
          metadata: Event.EventMetadata;
          subscribers: Set<Subscriber>;
        };
      };
/**
 * Get the ID of the thing.
 *
 * @returns {String} The ID.
 */
 getId(): string {
 return this.id;
}