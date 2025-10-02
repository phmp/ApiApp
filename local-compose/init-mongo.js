// This script is executed by the official MongoDB Docker image on first container startup.
// It creates the application database, an application-scoped user, and seeds some dummy data.

const appDbName = 'myapp';
const appUser = 'appuser';
const appPassword = 'apppassword';

// Connect to the desired database without shadowing the global `db`
const appDb = db.getSiblingDB(appDbName);

// Create an application-scoped user (read/write on app DB only)
try {
  appDb.createUser({
    user: appUser,
    pwd: appPassword,
    roles: [{ role: 'readWrite', db: appDbName }]
  });
} catch (e) {
  // Ignore if the user already exists (idempotency when re-running in dev)
  print(`User creation skipped or failed (possibly already exists): ${e}`);
}

// Ensure products collection exists
const existingCollections = appDb.getCollectionInfos().map(ci => ci.name);
if (!existingCollections.includes('products')) {
  appDb.createCollection('products');
}

// Seed dummy data only if empty
if (appDb.products.countDocuments() === 0) {
  appDb.products.insertMany([
    {
      name: 'Wireless Mouse',
      description: 'Ergonomic 2.4G wireless mouse',
      price: { amount: NumberDecimal('49.99'), currency: 'USD' }
    },
    {
      name: 'Mechanical Keyboard',
      description: 'RGB backlit blue switches',
      price: { amount: NumberDecimal('129.00'), currency: 'USD' }
    },
    {
      name: 'USB-C Hub',
      description: '7-in-1 with HDMI and card reader',
      price: { amount: NumberDecimal('39.50'), currency: 'EUR' }
    },
    {
      name: '4K Monitor',
      description: '27-inch UHD IPS display',
      price: { amount: NumberDecimal('1599.00'), currency: 'PLN' }
    },
    {
      name: 'Laptop Stand',
      description: 'Aluminum adjustable laptop stand',
      price: { amount: NumberDecimal('29.99'), currency: 'USD' }
    },
    {
      name: 'Noise-Cancelling Headphones',
      description: 'Over-ear ANC Bluetooth headphones',
      price: { amount: NumberDecimal('199.99'), currency: 'USD' }
    },
    {
      name: 'Web Camera 1080p',
      description: 'Full HD USB webcam with microphone',
      price: { amount: NumberDecimal('69.00'), currency: 'USD' }
    },
    {
      name: 'Portable SSD 1TB',
      description: 'USB-C external solid state drive',
      price: { amount: NumberDecimal('119.00'), currency: 'EUR' }
    },
    {
      name: 'Gaming Chair',
      description: 'Ergonomic reclining gaming chair',
      price: { amount: NumberDecimal('249.00'), currency: 'USD' }
    },
    {
      name: 'Desk Lamp',
      description: 'LED desk lamp with touch dimmer',
      price: { amount: NumberDecimal('24.90'), currency: 'EUR' }
    },
    {
      name: 'Smartphone Stand',
      description: 'Foldable desktop phone holder',
      price: { amount: NumberDecimal('14.99'), currency: 'USD' }
    },
    {
      name: 'Bluetooth Speaker',
      description: 'Portable waterproof speaker',
      price: { amount: NumberDecimal('89.00'), currency: 'USD' }
    },
    {
      name: 'Power Bank 20000mAh',
      description: 'High-capacity fast charging power bank',
      price: { amount: NumberDecimal('39.99'), currency: 'USD' }
    },
    {
      name: 'HDMI Cable 2m',
      description: 'Ultra HD 4K compatible HDMI cable',
      price: { amount: NumberDecimal('9.99'), currency: 'USD' }
    },
    {
      name: 'Ethernet Switch 8-Port',
      description: 'Gigabit unmanaged desktop switch',
      price: { amount: NumberDecimal('49.00'), currency: 'USD' }
    },
    {
      name: 'Graphic Tablet',
      description: 'Drawing tablet with pressure sensitivity',
      price: { amount: NumberDecimal('79.00'), currency: 'EUR' }
    },
    {
      name: 'Laser Printer',
      description: 'Compact monochrome laser printer',
      price: { amount: NumberDecimal('899.00'), currency: 'PLN' }
    },
    {
      name: 'Wireless Charger',
      description: '15W fast wireless charging pad',
      price: { amount: NumberDecimal('25.00'), currency: 'USD' }
    },
    {
      name: 'Smartwatch Band',
      description: 'Silicone replacement band',
      price: { amount: NumberDecimal('12.99'), currency: 'USD' }
    },
    {
      name: 'USB-C to Lightning Cable',
      description: 'MFi-certified fast charging cable',
      price: { amount: NumberDecimal('19.99'), currency: 'USD' }
    }
  ]);
}

print(`Initialized database '${appDbName}' with user '${appUser}' and seeded products (if needed).`);
