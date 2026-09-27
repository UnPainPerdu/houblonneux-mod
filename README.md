# Houblonneux
## Description
A Beer mod
TODO
___
___
## Changelog
(version prior to release is squashed into release one)
___
___
### Resource Pack note

- #### Beer Dispenser
How to add a custom model for this block :

1) Create a recipe for (you can ignore this step if the recipe already exists). 

→ mine are under houblonneux/houblonneux/dispenser_trade

JSON example :
```json
{
  "cost": {
    "count": 5,
    "id": "minecraft:emerald"
  },
  "result": {
    "id": "houblonneux:emerald_call_bottle"
  }
}
```

2) Create a model (I create mine with [Blockbench](https://blockbench.net/)) for it and place it under ``houblonneux/models/item/hack/dispenser/`` 

An example model can be found [here](exemple/models/dispenser/emerald_call_bottle_beer_dispenser.bbmodel) (./exemple/models/dispenser/emerald_call_bottle_beer_dispenser.bbmodel if link break)

IT MUST have the result name of the recipe with ``_beer_dispenser`` as suffix

3) Create a model definition as a conditional model similar to :
```json
{
  "model": {
    "type": "minecraft:condition",
    "component": "houblonneux:is_item_standalone_model_hack",
    "ignore_default": true,
    "on_false": {
      "type": "minecraft:model",
      "model": "houblonneux:item/emerald_call_bottle_generated" //here the regular model of the item result of recipe define upper
    },
    "on_true": {
      "type": "minecraft:model",
      "model": "houblonneux:item/hack/dispenser/emerald_call_bottle_beer_dispenser" //here your dispenser model
    },
    "property": "minecraft:has_component"
  }
}
```

4) Create a simple model used as a beacon for code

-> mine are under ``houblonneux/item/`` 

IT MUST have the same name as the model define in step 2

JSON example : 
```json
{
  "model": {
    "type": "minecraft:model",
    "model": "houblonneux:item/hack/dispenser/emerald_call_bottle_beer_dispenser"
  }
}
```