# 📈 Scaling Mobs

A Forge mod that dynamically scales boss difficulty based on the number of nearby players. The more players fighting a boss, the stronger it becomes!

## ✨ Features

- **Dynamic Scaling** – Boss health, damage, and armor increase with more nearby players
- **Configurable Boss List** – Define which mobs should be treated as bosses
- **Math-Based Scaling** – Scales using √(nearbyPlayers) for balanced difficulty progression

## 📥 Dependencies

- **Minecraft Forge** – Required on both client and server
- **Cloth Config API** – Required for GUI configuration (client-side only)

## 🎮 Supported Bosses (Default)

The mod comes with pre-configured bosses from popular mods:

| Mod | Bosses |
|-----|--------|
| Vanilla | Warden, Ender Dragon, Wither, Elder Guardian |
| Minecells | Concierge, Conjunctivius |
| Twilight Forest | Naga, Lich, Alpha Yeti, Ur-Ghast, Hydra, Minoshroom, Snow Queen |
| Alex's Mobs | Void Worm |
| Alex's Caves | Luxtructosaurus |
| Aquamirae | Captain Cornelia |
| Cataclysm | Maledictus, Ignis, Leviathan, Netherite Monstrosity, Harbinger, Ender Guardian, Ancient Remnant |
| Mowzie's Mobs | Ferrous Wroughtnaut, Frostmaw, Sculptor, Umvuthi |
| Iron's Spells & Spellbooks | Dead King |

## 📊 How Scaling Works

When a boss spawns or when players approach an existing boss:

**Health** = Base Health × √(Nearby Players)

**Damage** = Base Damage × √(Nearby Players)

**Armor** =
- If base armor > 0: Base Armor × √(Nearby Players)
- If base armor = 0: 5 + √(Nearby Players)

| Players | Scaling Multiplier |
|---------|-------------------|
| 1 | 1× |
| 4 | 2× |
| 9 | 3× |
| 16 | 4× |
| 25 | 5× |

-------